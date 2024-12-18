package org.freeplane.features.custom.preprocess

import org.freeplane.features.custom.treestream.StreamContext
import org.freeplane.features.custom.treestream.TreeNode

class TreeXmlNodeModel : TreeNode<XmlNodeModel> {
    constructor(node : XmlNodeModel) : super(node)

    private constructor(node : XmlNodeModel, context: StreamContext) : super(node, context, context)

    override fun XmlNodeModel.asTreeNode(context: StreamContext) =
        TreeXmlNodeModel(this, context)

    override val valueParent: XmlNodeModel?
        get() = value.parent

    override val valueChildren: Iterable<XmlNodeModel>
        get() = value.children

    override fun remove() {
        value.remove()
    }

    override fun stream(value: XmlNodeModel) = value.stream()
}

fun XmlNodeModel.stream() = TreeXmlNodeModel(this)