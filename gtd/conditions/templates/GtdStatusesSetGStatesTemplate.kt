package org.freeplane.features.gtd.conditions.templates

import org.freeplane.features.gtd.conditions.GtdGStatesComposedCondition
import org.freeplane.features.gtd.data.elements.GtdStatus

open class GtdStatusesSetGStatesTemplate(private val statuses : Collection<GtdStatus>,
                                         wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate(wrapee)
{
    override fun apply(composed: GtdGStatesComposedCondition) {
        super.apply(composed)

        statuses.forEach {
            if(!composed.containsStatus(it)) return@forEach

            composed.enableStatus(it)

            val condition = composed.getRawCondition(it)
            condition.reset()
        }
    }

    override fun reset(composed: GtdGStatesComposedCondition) {
        super.apply(composed)

        statuses.forEach {
            if(!composed.containsStatus(it)) return@forEach

            composed.enableStatus(it, false)

            val condition = composed.getRawCondition(it)
            condition.reset()
        }
    }

    override fun isAlreadyApplied(composed: GtdGStatesComposedCondition): Boolean {
        return super.isAlreadyApplied(composed) &&
                statuses.all {
                    if(composed.containsStatus(it)) composed.isStatusEnabled(it) else true
                }
    }
}