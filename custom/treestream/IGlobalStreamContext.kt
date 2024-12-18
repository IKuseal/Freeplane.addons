package org.freeplane.features.custom.treestream

interface IGlobalStreamContext {
    var isStopped : Boolean
    var depthLimit : Int

    var tagInheritance : Boolean
    var tagInheritanceRule : ((Any?) -> Any?)
}