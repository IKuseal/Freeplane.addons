package org.freeplane.features.gtd.data.special

import org.freeplane.features.gtd.data.elements.GtdStatusClass

sealed interface GtdNumRange {
    val name : String
    fun nameWithStatusClass(clazz : GtdStatusClass) = "${clazz.name}${name}"
}