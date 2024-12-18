package org.freeplane.features.custom.treestream

class LocalStreamContext(
    parentContext : ILocalStreamContext?,
    globalContext: IGlobalStreamContext
) : ILocalStreamContext {
    override var isStoppedChildren = false

    override var depth: Int = parentContext?.let { it.depth + 1 } ?: 1

    override var toSkip : Boolean = false

    override var tag: Any? = null

    override var childTag: Any? = null
        set(value) {
            childTagSet = true
            field = value
        }

    override var childTagSet: Boolean = false

    // tag inheritance
    init {
        if (globalContext.tagInheritance && parentContext != null) {
            tag = globalContext.tagInheritanceRule(inheritedTag)
        }
    }


}