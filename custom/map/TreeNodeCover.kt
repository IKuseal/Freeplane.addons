package org.freeplane.features.custom.map

import org.freeplane.features.custom.treestream.StreamContext
import org.freeplane.features.custom.treestream.TreeNode

class TreeNodeCover : TreeNode<NodeCover> {
    constructor(node : NodeCover) : super(node)

    private constructor(node : NodeCover, context: StreamContext) : super(node, context, context)

    override fun NodeCover.asTreeNode(context: StreamContext) =
        TreeNodeCover(this, context)

    override val valueParent: NodeCover?
        get() = value.parent

    override val valueChildren: Iterable<NodeCover>
        get() = value.children

    override fun remove() {
        TODO()
    }

    override fun stream(value: NodeCover) = value.stream()
}

fun NodeCover.stream() = TreeNodeCover(this)
