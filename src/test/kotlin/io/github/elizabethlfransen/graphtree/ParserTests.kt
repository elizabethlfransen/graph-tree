package io.github.elizabethlfransen.graphtree

import assertk.assertThat
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import io.github.elizabethlfransen.graphtree.internal.*
import org.antlr.v4.runtime.BailErrorStrategy
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.ParseCancellationException
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test

// we don't need lexer tests because if parser tests work then it can be assumed that lexer tests already work
class ParserTests {

    private data class ParseAndEvaluationResult(
        val input: String,
        val parsedResult: DocumentNode,
        val evaluatedResult: GraphTreeDocument,
        val name: String = "\"${input}\""
    )


    private val testCases = listOf(
        ParseAndEvaluationResult(
            "",
            DocumentNode(),
            GraphTreeDocument(),
            name = "empty document"
        ),
        ParseAndEvaluationResult(
            """
                test
            """.trimIndent(),
            DocumentNode(
                LabelNode("test")
            ),
            GraphTreeDocument(
                GraphTreeNode("test")
            )
        ),
        ParseAndEvaluationResult(
            """
                test
                    test1
            """.trimIndent(),
            DocumentNode(
                LabelNode(
                    "test",
                    LabelNode("test1")
                )
            ),
            GraphTreeDocument(
                GraphTreeNode(
                    "test",
                    GraphTreeNode("test1")
                )
            )
        ),
        ParseAndEvaluationResult(
            """
                test1
                    test1a
                test2
                    test2a
            """.trimIndent(),
            DocumentNode(
                LabelNode(
                    "test1",
                    LabelNode("test1a")
                ),
                LabelNode(
                    "test2",
                    LabelNode("test2a")
                )
            ),
            GraphTreeDocument(
                GraphTreeNode(
                    "test1",
                    GraphTreeNode("test1a")
                ),
                GraphTreeNode(
                    "test2",
                    GraphTreeNode("test2a")
                )
            )
        ),
        ParseAndEvaluationResult(
            """
                test1
                    test1a
                        test1aa
                test2
                    test2a
                        test2aa
            """.trimIndent(),
            DocumentNode(
                LabelNode(
                    "test1",
                    LabelNode(
                        "test1a",
                        LabelNode("test1aa")
                    )
                ),
                LabelNode(
                    "test2",
                    LabelNode(
                        "test2a",
                        LabelNode("test2aa")
                    )
                )
            ),
            GraphTreeDocument(
                GraphTreeNode(
                    "test1",
                    GraphTreeNode(
                        "test1a",
                        GraphTreeNode("test1aa")
                    )
                ),
                GraphTreeNode(
                    "test2",
                    GraphTreeNode(
                        "test2a",
                        GraphTreeNode("test2aa")
                    )
                )
            )
        )
    )

    private fun parse(input: String): DocumentNode {
        val lexer = GraphTreeLexer(CharStreams.fromString(input))
        val parser = GraphTreeParser(CommonTokenStream(lexer))
            .apply {
                errorHandler = BailErrorStrategy()
            }

        return parser
            .document()
            .ast
    }

    @Test
    fun parseTabDoesNotReturnDocument() {
        assertThat {
            parse("    ")
        }.isFailure()
            .isInstanceOf(ParseCancellationException::class)
    }

    @TestFactory
    fun testParsing() = testCases
        .map { case ->
            dynamicTest(
                """
                        given ${case.name}
                        when parsing the input
                        then it should result ${case.parsedResult}
                """.trimIndent()
            ) {
                assertThat(parse(case.input))
                    .isDataClassEqualTo(case.parsedResult)
            }
        }

    @TestFactory
    fun testEvaluating() = testCases
        .map {
            case ->
            dynamicTest(
                """
                        given ${case.name}
                        when evaluating the ast
                        then it should result ${case.evaluatedResult}
                """.trimIndent()
            ) {
                assertThat(Evaluator.visit(case.parsedResult))
                    .isDataClassEqualTo(case.evaluatedResult)
            }
        }
}