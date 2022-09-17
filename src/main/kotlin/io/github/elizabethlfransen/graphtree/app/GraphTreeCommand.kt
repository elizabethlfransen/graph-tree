package io.github.elizabethlfransen.graphtree.app

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.*
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.Graph
import io.github.elizabethlfransen.graphtree.GraphTreeDocument
import io.github.elizabethlfransen.graphtree.graphviz.GraphvizTranspiler
import java.io.InputStream

private fun InputStream.toGraphTreeDocument() =
    let(GraphTreeDocument::fromStream)

private fun GraphTreeDocument.toGraphviz() =
    let(GraphvizTranspiler::toGraphviz)

private fun Graph.compile() =
    let(Graphviz::fromGraph)

class GraphTreeCommand : CliktCommand(
    help = "Base command for graph-tree"
) {
    private val format by option("--format", "-f", help = "Resulting format for generating")
        .enum<Format>()
        .default(Format.PNG)

    private val input by option("--input", "-i", help = "Input file path or \"-\" for stdin")
        .inputStream()
        .defaultStdin()

    private val output by option("--output", "-o", help = "Output file path or \"-\" for stdout")
        .outputStream()
        .defaultStdout()

    override fun run() =
        input
            .toGraphTreeDocument()
            .toGraphviz()
            .compile()
            .render(format)
            .toOutputStream(output)
}