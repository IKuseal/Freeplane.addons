package org.freeplane.features.gtd.conditions.templates

import org.freeplane.features.gtd.conditions.base.ContainsRequirement.*
import org.freeplane.features.gtd.data.elements.GtdStateClass.*
import org.freeplane.features.gtd.data.elements.GtdSubStateClass.*

fun q1GStatesTemplate(wrapee : AGtdGStatesTemplate?) =
    GtdStatusesSetGStatesTemplate(Q1(), wrapee)

fun q2GStatesTemplate(wrapee : AGtdGStatesTemplate?) =
    GtdStatusesSetGStatesTemplate(Q2(), wrapee)

fun excludeRvGStatesTemplate(wrapee: AGtdGStatesTemplate?) =
    GtdSubStateLimitingsGStateTemplate(CD(), EXCLUDE, wrapee)

fun excludeCdGStatesTemplate(wrapee: AGtdGStatesTemplate?) =
    GtdSubStateLimitingsGStateTemplate(CD(), EXCLUDE, wrapee)


