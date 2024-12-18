package org.freeplane.features.custom.treestream

import org.freeplane.features.custom.ITSELF_RETURN_FUN

class GlobalStreamContext : IGlobalStreamContext {
    override var isStopped = false

    override var depthLimit: Int = Int.MAX_VALUE

    override var tagInheritance = false

    override var tagInheritanceRule: ((Any?) -> Any?) = ITSELF_RETURN_FUN
        set(value) {
            tagInheritance = true
            field = value
        }
}