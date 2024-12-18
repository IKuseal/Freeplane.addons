package org.freeplane.features.gtd.filter.statefilter

import org.freeplane.features.gtd.conditions.templates.AGtdGStatesTemplate
import org.freeplane.features.gtd.conditions.templates.GtdStatusesSetGStatesTemplate
import org.freeplane.features.gtd.data.elements.GtdNum.*
import org.freeplane.features.gtd.data.elements.GtdStateClass.*

fun iGStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(I(), wrapee)

fun l0GStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(arrayListOf(L(S0)), wrapee)

fun l01GStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(L(S0, S1), wrapee)

fun l012GStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(L(S0, S1, S2), wrapee)

fun l0123GStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(L(S0, S1, S2, S3), wrapee)

fun q0GStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(arrayListOf(Q1(S0)), wrapee)

fun q01GStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(Q1(S0, S1), wrapee)

fun q012GStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(Q1(S0, S1, S2), wrapee)

fun q0123GStatesTemplate(wrapee : AGtdGStatesTemplate?) : AGtdGStatesTemplate =
    GtdStatusesSetGStatesTemplate(Q1(S0, S1, S2, S3), wrapee)