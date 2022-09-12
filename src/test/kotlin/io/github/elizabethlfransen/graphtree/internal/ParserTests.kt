package io.github.elizabethlfransen.graphtree.internal

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Test

// we don't need lexer tests because if parser tests work then it can be assumed that lexer tests already work
class ParserTests {

    private fun parse(input: String): DocumentNode {
        val lexer = GraphTreeLexer(CharStreams.fromString(input))
        return GraphTreeParser(CommonTokenStream(lexer))
            .document()
            .ast
    }

    @Test
    fun parseEmptyDocumentNode() {
        val actualNode = parse("")
        assertThat(actualNode).isEqualTo(
            DocumentNode()
        )
    }

    @Test
    fun parseSingleNode() {
        val actualNode = parse(
            """
                test
            """.trimIndent()
        )
        assertThat(actualNode)
            .isEqualTo(
                DocumentNode(
                    LabelNode("test")
                )
            )
    }

    @Test
    fun parseSingleTree() {
        val actualNode = parse(
            """
                test
                    test1
            """.trimIndent()
        )
        assertThat(actualNode)
            .isEqualTo(
                DocumentNode(
                    LabelNode(
                        "test",
                        LabelNode("test1")
                    )
                )
            )
    }

    @Test
    fun parseMultipleTrees() {
        val actualNode = parse(
            """
                test1
                    test1a
                test2
                    test2a
            """.trimIndent()
        )
        assertThat(actualNode)
            .isEqualTo(
                DocumentNode(
                    LabelNode(
                        "test1",
                        LabelNode("test1a")
                    ),
                    LabelNode(
                        "test2",
                        LabelNode("test2a")
                    )
                )
            )
    }

    @Test
    fun parseDeepTrees() {
        val actualNode = parse(
            """
                test1
                    test1a
                        test1aa
                test2
                    test2a
                        test2aa
            """.trimIndent()
        )
        assertThat(actualNode)
            .isEqualTo(
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
                )
            )
    }

}