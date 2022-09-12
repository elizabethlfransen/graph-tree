package io.github.elizabethlfransen.graphtree.internal

internal interface Visitor<T> {
    fun visit(node: LabelNode): T
    fun visit(node: DocumentNode): T
}