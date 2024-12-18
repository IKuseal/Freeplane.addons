package org.freeplane.features.gtd.transparency

import org.freeplane.features.custom.condition.not
import org.freeplane.features.custom.inverse
import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.filter.condition.ConjunctConditions
import org.freeplane.features.filter.condition.DisjunctConditions
import org.freeplane.features.gtd.conditions.GtdGStateCondition
import org.freeplane.features.gtd.conditions.GtdGStatesComposedConditionFactory
import org.freeplane.features.gtd.conditions.GtdGStateConditionFactory
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.GtdStateClass.*
import org.freeplane.features.gtd.data.elements.GtdSubStateClass.*
import org.freeplane.features.gtd.data.elements.GtdStatus
import org.freeplane.features.gtd.data.elements.GtdSubState
import org.freeplane.features.gtd.intent.condition.GtdNodeCTagIncludeCondition
import org.freeplane.features.gtd.intent.condition.GtdTagIncludeCondition
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.map.cover.GtdMapCover
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.map.cover.event.IGtdMapCoverChangeListener
import org.freeplane.features.gtd.node.forEachWithSpread
import org.freeplane.features.gtd.tag.GTD_TAG_ARTIFACT_REVIEW
import org.freeplane.features.gtd.transparency.actions.GtdTransparencyResetAction
import org.freeplane.features.gtd.transparency.actions.GtdTransparencyTemplateMenuBuilder
import org.freeplane.features.gtd.transparency.actions.GtdTransparencyTemplateMenuBuilder.GTD_TRANSPARENCY_TEMPLATE_MENU_BUILDER
import org.freeplane.features.gtd.transparency.actions.SwitchGtdTransparencyAccumulationModeAction

object GtdTransparencyController : IGtdMapCoverChangeListener {
    val gtdMapCoverUpdatedTransparencyEventProp = GtdEventProp(GtdMapCoverController.gtdMapCoverUpdatedEventProp)

    fun init() {
        GtdController.run {
            addAction(ShowGtdTransparencyDialogAction())
            addAction(GtdTransparencyResetAction())
            addAction(SwitchGtdTransparencyAccumulationModeAction())
            addUiBuilder(GTD_TRANSPARENCY_TEMPLATE_MENU_BUILDER, GtdTransparencyTemplateMenuBuilder)
        }
    }

    // transparency data ***********************************************************************************
    fun createTransparencyData(mapCover: GtdMapCover = GtdMapCoverController.currentGtdMapCover) =
        mapCover.transparencyData ?: initTransparencyData(mapCover).also { mapCover.transparencyData = it }

    private fun initTransparencyData(mapCover: GtdMapCover) : GtdTransparencyData {
        return GtdTransparencyData(
            GtdGStatesComposedConditionFactory(GtdGStateConditionFactory())()).also {
                classicalTransparencyTemplate.apply(it.transparencyConditionData)
            mapCover.transparencyData = it
        }
    }

    fun transparencyConditionData(mapCover: GtdMapCover = GtdMapCoverController.currentGtdMapCover) = createTransparencyData(mapCover).transparencyConditionData

    private fun transparencyConditions(mapCover : GtdMapCover) : Pair<ASelectableCondition?, ASelectableCondition?> {
        val extraCondition = GtdNodeCTagIncludeCondition(GTD_TAG_ARTIFACT_REVIEW).inverse

        val extendedCondition : ASelectableCondition.() -> ASelectableCondition = {
            ConjunctConditions.combine(extraCondition, this)
        }

        val data = transparencyConditionData(mapCover)

        val rawConditionsByIsTree = data.enabledRawConditions.groupBy { it.isTransparencyTree }

        var ownOnlyCondition : ASelectableCondition? = null
        var treeCondition : ASelectableCondition? = null

        rawConditionsByIsTree[false]
            ?.takeIf { it.isNotEmpty() }
            ?.map { it() }
            ?.let { ownOnlyCondition = DisjunctConditions.combine(*it.toTypedArray()).extendedCondition() }

        rawConditionsByIsTree[true]
            ?.takeIf { it.isNotEmpty() }
            ?.map { it() }
            ?.let { treeCondition = DisjunctConditions.combine(*it.toTypedArray()).extendedCondition() }

        return ownOnlyCondition to treeCondition
    }

    // transparency calculations ********************************************************************************
    // listening changes
    override fun onGtdMapCoverChange(map: GtdMapCover, prop: GtdEventProp) {
        if(prop.iz(GtdMapCoverController.gtdMapCoverUpdatedMainEventProp)) updateTransparency(map)
    }

    // updating
    fun updateTransparency(mapCover: GtdMapCover = GtdMapCoverController.currentGtdMapCover) {
        calculateTransparency(mapCover)
        GtdMapCoverController.fireGtdMapCoverChange(mapCover, gtdMapCoverUpdatedTransparencyEventProp)
    }

    private fun calculateTransparency(mapCover: GtdMapCover) {
        val root = mapCover.root

        val (ownOnlyCondition, treeCondition) = transparencyConditions(mapCover)

        root.forEachWithSpread(false) { spread ->
            var newSpread = spread

            if(intents.any { it.gcsType != GcsType.LOCAL })
                newSpread = false

            isTransparent = newSpread

            when {
                newSpread -> {
                }

                treeCondition?.checkNode(node) ?: false -> {
                    newSpread = true
                    isTransparent = newSpread
                }

                ownOnlyCondition?.checkNode(node) ?: false -> isTransparent = true
            }

            return@forEachWithSpread newSpread
        }
    }

    // manage **********************************************************************************************
    fun reset(mapCover: GtdMapCover = GtdMapCoverController.currentGtdMapCover) {
        transparencyConditionData(mapCover).reset()
        updateTransparency(mapCover)
    }
}

val GtdGStateCondition.isTransparencyTree get() = status.isTransparencyTree

val GtdStatus.isTransparencyTree get() = isTransparencyTreeOfStatuses[this] ?: true

private val isTransparencyTreeOfStatuses = hashMapOf<GtdStatus, Boolean>().apply {
//    GtdState.values(I).forEach { put(it, false) }
//    GtdState.values(I2).forEach { put(it, false) }
    GtdState.values(C).forEach { put(it, false) }
    GtdSubState.values(DSC).forEach { put(it, false) }
    GtdSubState.values(CD).forEach { put(it, false) }
}