package io.github.elizabethlfransen.graphtree.testutil

import io.github.elizabethlfransen.graphtree.dsl.GraphTreeDocumentMarker
import io.github.elizabethlfransen.graphtree.dsl.GraphTreeNodeMarker
import io.github.elizabethlfransen.graphtree.internal.DocumentNode
import io.github.elizabethlfransen.graphtree.internal.LabelNode

@GraphTreeNodeMarker
internal data class LabelNodeInit internal constructor(
    val label: String,
    val children: MutableList<LabelNode> = mutableListOf()
) {
    fun node(label: String, init: LabelNodeInit.() -> Unit = {}) {
        children.add(
            LabelNodeInit(label)
                .apply(init)
                .build()
        )
    }

    operator fun String.invoke(init: LabelNodeInit.() -> Unit = {} ) =
        node(this, init)

    internal fun build() = LabelNode(label, children)
}

@GraphTreeDocumentMarker
internal data class DocumentNodeInit internal constructor(val children: MutableList<LabelNode> = mutableListOf()) {
    fun node(label: String, init: LabelNodeInit.() -> Unit = {}) {
        children.add(
            LabelNodeInit(label)
                .apply(init)
                .build()
        )
    }

    operator fun String.invoke(init: LabelNodeInit.() -> Unit = {} ) =
        node(this, init)

    internal fun build() = DocumentNode(children)
}

internal fun labelNode(label: String, init: LabelNodeInit.() -> Unit = {}) =
    LabelNodeInit(label)
        .apply(init)
        .build()

internal fun docNode(init: DocumentNodeInit.() -> Unit = {}) =
    DocumentNodeInit()
        .apply(init)
        .build()