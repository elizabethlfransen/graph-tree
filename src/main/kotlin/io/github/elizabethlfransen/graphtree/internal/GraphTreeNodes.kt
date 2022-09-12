package io.github.elizabethlfransen.graphtree.internal

internal sealed interface GraphTreeNode

internal data class DocumentNode(val children: List<LabelNode>) : GraphTreeNode {
    constructor(vararg nodes: LabelNode) : this(nodes.toList())
}

internal data class LabelNode(val label: String, val children: List<LabelNode>) : GraphTreeNode
{
    constructor(label: String, vararg nodes: LabelNode): this(label, nodes.toList())
}