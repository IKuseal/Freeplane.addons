package org.freeplane.features.gtd.conditions

import org.freeplane.features.gtd.data.elements.GtdStatus

fun interface IGtdGStateConditionFactory {
    fun create(status : GtdStatus) : GtdGStateCondition?
}