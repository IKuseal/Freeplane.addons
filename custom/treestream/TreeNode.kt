package org.freeplane.features.custom.treestream

abstract class TreeNode<T : Any>(
    val value : T,
    globalStreamContext: IGlobalStreamContext = GlobalStreamContext(),
    parentLocalStreamContext: ILocalStreamContext? = null
//    parent: TreeNode<T>? = null
) : StreamContext,
//    IGlobalStreamContext by parent ?: GlobalStreamContext(),
    IGlobalStreamContext by globalStreamContext,
    ILocalStreamContext by LocalStreamContext(parentLocalStreamContext, globalStreamContext)
{
    private var isParentInitialized = false

    var parent: TreeNode<T>? = null
        get() {
            if(!isParentInitialized) {
                parent = valueParent?.asParentTreeNode()
                isParentInitialized = true
            }

            return field
        }

    val children: Iterable<TreeNode<T>>
        get() = valueChildren.map { it.asChildTreeNode() }

    private fun T.asChildTreeNode() : TreeNode<T> =
        asTreeNode().also { it.parent = this@TreeNode }

    private fun T.asParentTreeNode() : TreeNode<T> =
        asTreeNode().also { this@TreeNode.parent = it }

    private fun T.asTreeNode() : TreeNode<T> =
        asTreeNode(this@TreeNode)

    protected abstract fun T.asTreeNode(context : StreamContext) : TreeNode<T>

    protected abstract val valueParent : T?

    protected abstract val valueChildren : Iterable<T>

    abstract fun remove()

    abstract fun stream(value : T) : TreeNode<T>

    // context **********************************************************************************************
    // it is meant on children
    override val isDepthWithinLimit: Boolean
        get() = depth <= depthLimit

    override val isChildDepthWithinLimit: Boolean
        get() = depth < depthLimit

    override val toContinue: Boolean
        get() = !isStopped && !isStoppedChildren && isChildDepthWithinLimit
}