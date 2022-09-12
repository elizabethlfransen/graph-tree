package io.github.elizabethlfransen.graphtree

/**
 * Represents root node of a GraphTree document which may contain 0 or more GraphTreeNodes.
 *
 * Each direct child of the document are considered to be their own tree.
 */
data class GraphTreeDocument(
    /**
     * Trees contained within document
     */
    val children: GraphTreeNode
)