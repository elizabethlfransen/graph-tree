package io.github.elizabethlfransen.graphtree

/**
 * Represents a node within the GraphTree document.
 *
 * Each node may or may not be a tree root.
 *
 * Each node may have zero or more child nodes.
 */
data class GraphTreeNode(
    /** The label of the node */
    val label: String,
    /** The child nodes */
    val children: List<GraphTreeNode>
) {
    constructor(label: String, vararg children: GraphTreeNode) : this(label, children.toList())
}