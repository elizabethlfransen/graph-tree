package io.github.elizabethlfransen.graphtree.internal

internal sealed interface Visitor<T> {
    fun visit(node: LabelNode): T
    fun visit(node: DocumentNode): T
}