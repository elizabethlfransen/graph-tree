package io.github.elizabethlfransen.graphtree.dsl

import io.github.elizabethlfransen.graphtree.GraphTreeDocument
import io.github.elizabethlfransen.graphtree.GraphTreeNode

@GraphTreeNodeMarker
data class GraphTreeNodeInit internal constructor(
    val label: String,
    val children: MutableList<GraphTreeNode> = mutableListOf()
) {
    fun node(label: String, init: GraphTreeNodeInit.() -> Unit = {}) {
        children.add(
            GraphTreeNodeInit(label)
                .apply(init)
                .build()
        )
    }

    internal fun build() = GraphTreeNode(label, children)
}

@GraphTreeDocumentMarker
data class GraphTreeDocumentInit internal constructor(val children: MutableList<GraphTreeNode> = mutableListOf()) {
    fun node(label: String, init: GraphTreeNodeInit.() -> Unit = {}) {
        children.add(
            GraphTreeNodeInit(label)
                .apply(init)
                .build()
        )
    }

    internal fun build() = GraphTreeDocument(children)
}

fun node(label: String, init: GraphTreeNodeInit.() -> Unit = {}) =
    GraphTreeNodeInit(label)
        .apply(init)
        .build()

fun graphTreeDocument(init: GraphTreeDocumentInit.() -> Unit = {}) =
    GraphTreeDocumentInit()
        .apply(init)
        .build()