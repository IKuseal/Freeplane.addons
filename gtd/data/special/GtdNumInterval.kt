package org.freeplane.features.gtd.data.special

import org.freeplane.features.gtd.data.elements.GtdNum
import org.freeplane.features.gtd.data.elements.GtdStatusClass

data class GtdNumInterval(val first : GtdNum, val second : GtdNum) : GtdNumRange {
    override val name: String get() = "$first-$second"

}