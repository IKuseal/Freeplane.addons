package org.freeplane.features.gtd.transparency

import org.freeplane.features.gtd.conditions.templates.AGtdGStatesTemplate
import org.freeplane.features.gtd.conditions.templates.GtdStatusesSetGStatesTemplate
import org.freeplane.features.gtd.data.elements.GtdNum.S0
import org.freeplane.features.gtd.data.elements.GtdNum.S1
import org.freeplane.features.gtd.data.elements.GtdStateClass
import org.freeplane.features.gtd.data.elements.GtdStateClass.*
import org.freeplane.features.gtd.data.elements.GtdStatus

fun q5to9GStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(GtdStateClass.Q1(S0, S1), wrapee)

val classicalTransparencyTemplate = initClassical()

private fun initClassical() : AGtdGStatesTemplate {
    var template : AGtdGStatesTemplate = GtdStatusesSetGStatesTemplate(
        mutableListOf<GtdStatus>().apply {
            addAll(F())
            addAll(I2())
            addAll(R())
            addAll(C())
//            addAll(Z())
            addAll(Y2())
            addAll(Y0())
            addAll(W2())
            addAll(W1())
            addAll(W0())
            addAll(E2())
            addAll(E1())
            addAll(E0())
            addAll(S())
            addAll(U2())
            addAll(U0())
            addAll(O())
            addAll(Q2())
            addAll(Q1())
        }, null)

    return template
}