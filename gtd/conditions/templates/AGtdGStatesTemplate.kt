package org.freeplane.features.gtd.conditions.templates

import org.freeplane.features.gtd.conditions.GtdGStatesComposedCondition

abstract class AGtdGStatesTemplate(
//    val name : String,
    var wrapee : AGtdGStatesTemplate? = null) {

//    open val fullName : String get() = (wrapee?.fullName ?: "") + name

    open fun apply(composed : GtdGStatesComposedCondition) {
        wrapee?.apply(composed)
    }

    open fun reset(composed : GtdGStatesComposedCondition) {
        wrapee?.reset(composed)
    }

    open fun isAlreadyApplied(composed : GtdGStatesComposedCondition) : Boolean {
        return wrapee?.isAlreadyApplied(composed) ?: true
    }

    open fun switch(composed : GtdGStatesComposedCondition) {
        if(!isAlreadyApplied(composed)) apply(composed)
        else reset(composed)
    }
}