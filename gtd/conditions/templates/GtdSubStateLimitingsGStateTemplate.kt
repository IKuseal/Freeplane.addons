package org.freeplane.features.gtd.conditions.templates

import org.freeplane.features.gtd.conditions.GtdGStatesComposedCondition
import org.freeplane.features.gtd.conditions.base.ContainsRequirement
import org.freeplane.features.gtd.data.elements.GtdSubState

class GtdSubStateLimitingsGStateTemplate(val subStateLimitings : Collection<Pair<GtdSubState, ContainsRequirement>>,
                                         wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate(wrapee)
{
    constructor(subStates : Collection<GtdSubState>, limiting : ContainsRequirement, wrapee: AGtdGStatesTemplate?)
            : this(subStates.map { it to limiting }, wrapee)

    override fun apply(composed: GtdGStatesComposedCondition) {
        super.apply(composed)

        subStateLimitings.forEach { composed.setLimiting(it) }
    }
}