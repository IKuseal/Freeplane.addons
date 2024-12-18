package org.freeplane.features.custom.treestream

interface ILocalStreamContext {
    var isStoppedChildren : Boolean

    val depth : Int
    var toSkip: Boolean

    var tag : Any?
    var childTag : Any?
    var childTagSet : Boolean
    val inheritedTag : Any?
        get() = if(childTagSet) childTag else tag
}