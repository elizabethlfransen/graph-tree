package io.github.elizabethlfransen.graphtree

import io.github.elizabethlfransen.graphtree.internal.Evaluator
import io.github.elizabethlfransen.graphtree.internal.GraphTreeLexer
import io.github.elizabethlfransen.graphtree.internal.GraphTreeParser
import org.antlr.v4.runtime.BailErrorStrategy
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream

/**
 * Reads a [GraphTreeDocument] given some sort of [inputStream]
 */
class GraphTreeDocumentReader(inputStream: CharStream) {
    private val parser = GraphTreeParser(
        CommonTokenStream(
            GraphTreeLexer(
                inputStream
            )
        )
    )
        .apply {
            errorHandler = BailErrorStrategy()
        }


    fun read() = Evaluator.visit(parser.document().ast)
}