package io.github.elizabethlfransen.graphtree.internal

import io.github.elizabethlfransen.graphtree.GraphTreeDocument
import io.github.elizabethlfransen.graphtree.GraphTreeNode

internal object Evaluator : Visitor<Any> {
    override fun visit(node: LabelNode): GraphTreeNode {
        return GraphTreeNode(
            node.label,
            node
                .children
                .map(::visit)
        )
    }

    override fun visit(node: DocumentNode): GraphTreeDocument {
        return GraphTreeDocument(
            node
                .children
                .map(::visit)
        )
    }
}