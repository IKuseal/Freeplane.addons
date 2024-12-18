package org.freeplane.features.custom.treestream

interface StreamContext : ILocalStreamContext, IGlobalStreamContext {
    val toContinue : Boolean
    val isDepthWithinLimit : Boolean
    val isChildDepthWithinLimit : Boolean
}