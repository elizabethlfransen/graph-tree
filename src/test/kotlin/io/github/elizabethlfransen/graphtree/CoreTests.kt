package io.github.elizabethlfransen.graphtree

import assertk.assertThat
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import guru.nidi.graphviz.attribute.Attributes.attr
import guru.nidi.graphviz.graph
import guru.nidi.graphviz.model.MutableGraph
import io.github.elizabethlfransen.graphtree.dsl.graphTreeDocument
import io.github.elizabethlfransen.graphtree.graphviz.GraphvizTranspiler
import io.github.elizabethlfransen.graphtree.graphviz.internal.label
import io.github.elizabethlfransen.graphtree.internal.DocumentNode
import io.github.elizabethlfransen.graphtree.internal.Evaluator
import io.github.elizabethlfransen.graphtree.internal.GraphTreeLexer
import io.github.elizabethlfransen.graphtree.internal.GraphTreeParser
import io.github.elizabethlfransen.graphtree.testutil.docNode
import org.antlr.v4.runtime.BailErrorStrategy
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.ParseCancellationException
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test

// we don't need lexer tests because if parser tests work then it can be assumed that lexer tests already work
class CoreTests {

    private data class ParseAndEvaluationResult(
        val input: String,
        val parsedResult: DocumentNode,
        val evaluatedResult: GraphTreeDocument,
        val graphvizTranspileResult: MutableGraph,
        val name: String = "\"${input}\""
    )


    private val testCases = listOf(
        ParseAndEvaluationResult(
            input = "",
            parsedResult = docNode(),
            evaluatedResult = graphTreeDocument(),
            name = "empty document",
            graphvizTranspileResult = graph { }
        ),
        ParseAndEvaluationResult(
            input = """
                            test
                        """.trimIndent(),
            parsedResult = docNode {
                "test" {
                }
            },
            evaluatedResult = graphTreeDocument {
                "test" {
                }
            },
            graphvizTranspileResult = graph {
                "n0"[attr("label", "test")]
            }
        ),
        ParseAndEvaluationResult(
            input = """
                            test
                                test1
                        """.trimIndent(),
            parsedResult = docNode {
                "test" {
                    "test1" {
                    }
                }
            },
            evaluatedResult = graphTreeDocument {
                "test" {
                    "test1" {
                    }
                }
            },
            graphvizTranspileResult = graph {
                "n0"[label("test")] - "n0n0"[label("test1")]
            }
        ),
        ParseAndEvaluationResult(
            input = """
                            test1
                                test1a
                            test2
                                test2a
                        """.trimIndent(),
            parsedResult = docNode {
                "test1" {
                    "test1a" {
                    }
                }
                "test2" {
                    "test2a" {
                    }
                }
            },
            evaluatedResult = graphTreeDocument {
                "test1" {
                    "test1a" {
                    }
                }
                "test2" {
                    "test2a" {
                    }
                }
            },
            graphvizTranspileResult = graph {
                "n0"[label("test1")] - "n0n0"[label("test1a")]
                "n1"[label("test2")] - "n1n0"[label("test2a")]
            }
        ),
        ParseAndEvaluationResult(
            input = """
                            test1
                                test1a
                                    test1aa
                            test2
                                test2a
                                    test2aa
                        """.trimIndent(),
            parsedResult = docNode {
                "test1" {
                    "test1a" {
                        "test1aa" {
                        }
                    }
                }
                "test2" {
                    "test2a" {
                        "test2aa" {
                        }
                    }
                }
            },
            evaluatedResult = graphTreeDocument {
                "test1" {
                    "test1a" {
                        "test1aa" {
                        }
                    }
                }
                "test2" {
                    "test2a" {
                        "test2aa" {
                        }
                    }
                }
            },
            graphvizTranspileResult = graph {
                "n0"[label("test1")] - "n0n0"[label("test1a")] - "n0n0n0"[label("test1aa")]
                "n1"[label("test2")] - "n1n0"[label("test2a")] - "n1n0n0"[label("test2aa")]
            }
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
        .map { case ->
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

    @TestFactory
    fun testTranspiling() = testCases
        .map { case ->
            dynamicTest(
                """
                    given ${case.name}
                    when transpiling the document
                    then it should result ${case.graphvizTranspileResult}
                """.trimIndent()
            ) {
                assertThat(GraphvizTranspiler.toGraphviz(case.evaluatedResult).toString())
                    .isEqualTo(case.graphvizTranspileResult.toString())
            }
        }
}