package org.freeplane.features.gtd.data.special

import org.freeplane.features.gtd.data.elements.GtdNum

data class GtdNumAlone(val value : GtdNum) : GtdNumRange {
    override val name: String get() = "$value"
}