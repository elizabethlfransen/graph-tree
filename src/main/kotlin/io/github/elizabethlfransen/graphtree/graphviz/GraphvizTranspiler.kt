package io.github.elizabethlfransen.graphtree.graphviz

import guru.nidi.graphviz.model.Factory.graph
import guru.nidi.graphviz.model.Factory.node
import guru.nidi.graphviz.model.Graph
import guru.nidi.graphviz.model.Node
import io.github.elizabethlfransen.graphtree.GraphTreeDocument
import io.github.elizabethlfransen.graphtree.GraphTreeNode
import io.github.elizabethlfransen.graphtree.graphviz.internal.label

object GraphvizTranspiler {
    fun toGraphviz(document: GraphTreeDocument): Graph {
        return graph()
            .with(
                document
                    .children
                    .mapIndexed(GraphvizTranspiler::toGraphviz)
            )
    }

    private fun toGraphviz(index: Int, node: GraphTreeNode, parentNodeId: String = ""): Node {
        val id = "${parentNodeId}n${index}"

        fun childToGraphviz(index: Int, node: GraphTreeNode) =
            toGraphviz(index, node, id)

        return node(id)
            .label(node.label)
            .link(
                *node
                    .children
                    .mapIndexed(::childToGraphviz)
                    .toTypedArray()
            )
    }
}