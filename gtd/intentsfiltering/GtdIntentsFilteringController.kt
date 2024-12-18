package org.freeplane.features.gtd.intentsfiltering

import org.freeplane.features.gtd.conditions.base.ContainsRequirement
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.map.cover.GtdMapCover
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.tag.GTD_PLANE_DEFAULT
import org.freeplane.features.gtd.tag.GTD_PLANE_DELIVERY
import java.util.WeakHashMap

object GtdIntentsFilteringController {
    fun init() {
        GtdController.addAction(EditGtdIntentsFilteringAction())
    }

    // condition model *************************************************************************************
    private val conditionModels : MutableMap<GtdMapCover, IntentsFilteringConditionModel> = WeakHashMap()

    private val GtdMapCover.conditionModelInternal : IntentsFilteringConditionModel
        get() = conditionModels[this] ?: createStandardIntentsFilteringModel().also { conditionModels[this] = it }

    fun getConditionModel(gtdMapC: GtdMapCover) =
        gtdMapC.conditionModelInternal

    // * * *
    fun onConditionModelChange(gtdMapC: GtdMapCover) {
        gtdMapC.intentsFilteringConditionModel.revalidate()
        GtdMapCoverController.fireGtdMapCoverChange(gtdMapC, GtdMapCoverController.gtdMapCoverDataUpdatedProp)
    }

    private fun createStandardIntentsFilteringModel() = IntentsFilteringConditionModel().apply {
        planesModel.keys.filterNot { it == GTD_PLANE_DEFAULT || it == GTD_PLANE_DELIVERY }
            .forEach {
                planesModel[it] = ContainsRequirement.EXCLUDE
            }
    }
}

val GtdMapCover.intentsFilteringConditionModel get() =
    GtdIntentsFilteringController.getConditionModel(this)