package org.freeplane.features.custom.map

import org.freeplane.features.custom.treestream.StreamContext
import org.freeplane.features.custom.treestream.TreeNode
import org.freeplane.features.map.NodeModel

class TreeNodeModel : TreeNode<NodeModel> {
    constructor(node : NodeModel) : super(node)

    private constructor(node : NodeModel, context: StreamContext) : super(node, context, context)

    override fun NodeModel.asTreeNode(context: StreamContext) =
        TreeNodeModel(this, context)

    override val valueParent: NodeModel?
        get() = value.parentNode

    override val valueChildren: Iterable<NodeModel>
        get() = value.children

    override fun remove() {
        TODO()
    }

    override fun stream(value: NodeModel) = value.stream()
}

fun NodeModel.stream() = TreeNodeModel(this)
