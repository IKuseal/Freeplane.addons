package org.freeplane.features.gtd.core

class GtdEventProp(val parent : GtdEventProp?, val setsDirtyFlag : Boolean = true) {
    fun iz(event : GtdEventProp) : Boolean =
        this == event || (parent?.let { it.iz(event) } ?: false)
}

val gtdEventProp = GtdEventProp(null)