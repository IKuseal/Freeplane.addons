package org.freeplane.features.gtd.filter

import org.freeplane.features.custom.frcondition.ConditionCooperationType
import org.freeplane.features.custom.frcondition.ConditionCooperationType.*
import org.freeplane.features.custom.frcondition.conjunctCondition
import org.freeplane.features.custom.frcondition.containsReferential
import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.filter.condition.ConjunctConditions
import org.freeplane.features.filter.condition.DisjunctConditions
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.filter.actions.ApplyGtdFilterAction
import org.freeplane.features.gtd.filter.events.IGtdFilteringListener
import org.freeplane.features.gtd.filter.layersfilter.GtdLayersFilterController
import org.freeplane.features.gtd.filter.modulefilter.GtdModuleFilterController
import org.freeplane.features.gtd.filter.statefilter.GtdStatesFilterController
import org.freeplane.features.gtd.map.cover.GtdMapCoverController

object GtdFilterController {
    fun init() {
        GtdStatesFilterController.init()
        GtdModuleFilterController.init()
        GtdLayersFilterController.init()
        GtdController.run {
            addAction(ApplyGtdFilterAction(AND))
            addAction(ApplyGtdFilterAction(OR))
            addAction(ApplyGtdFilterAction(DOMINATE))
        }
    }

    private val subFilterControllers : Collection<AGtdSubFilterController> =
        arrayListOf<AGtdSubFilterController>().apply {
            add(GtdStatesFilterController)
            add(GtdModuleFilterController)
            add(GtdLayersFilterController)
        }

    // filter-data **********************************************************************************
    val filterData : GtdFilterData get() {
        val mapCover = GtdMapCoverController.currentMapCover
        return mapCover.filterData ?: GtdFilterData().also { mapCover.filterData = it }
    }

    // condition ********************************************************************************************
    private val integralCondition : ASelectableCondition?
        get() {
            val conditions = arrayListOf<ASelectableCondition>()

            subFilterControllers
                .mapNotNull { it.condition }
                .takeIf { it.isNotEmpty() }
                ?.let { conditions.addAll(it) }

            conditions.addAll(filterData.elementConditions.values)


            if(conditions.isEmpty()) return null

            return conjunctCondition(conditions)
        }

    fun onIntegralConditionChanged() {
        if(filterData.isFilteringSet) reapplyFilter()
        else applyFilter(DOMINATE)
    }

    // apply ********************************************************************************************
    fun applyFilter(cooperationType : ConditionCooperationType = DOMINATE) {
        // reset, warning on recode this
        filterData.condition = null

        fireBeforeFilteringComposed()

        integralCondition
            ?.run {
                filterData.condition = this
                filterData.conditionWrapper
            }
            ?.combineWithFrCondition(cooperationType)
            ?.let {
                filterController.apply(it)
            }
    }

    fun reapplyFilter() {
        fireBeforeFilteringComposed()

        val condition = integralCondition

        if(condition == null) {
            filterData.condition = null
            filterController.applyNoFiltering(GtdController.currentMap)
            return
        }

        fun validateFilter() : Boolean {
            if(!filterData.isFilteringSet) return false

            return frCondition?.containsReferential(filterData.conditionWrapper!!) ?: false
        }

        if(!validateFilter())
            applyFilter(DOMINATE)
        else {
            filterData.condition = integralCondition
            filterController.myApplyFilter(true)
        }
    }

    private fun ASelectableCondition.combineWithFrCondition(
        cooperationType: ConditionCooperationType
    ) : ASelectableCondition
    {
        val frCondition = frCondition ?: return this

        return when(cooperationType) {
            AND -> ConjunctConditions.combine(this, frCondition)
            OR -> DisjunctConditions.combine(this, frCondition)
            else -> this
        }
    }

    // element conditions *****************************************************************************
    val elementConditions get() = filterData.elementConditions

    fun addElementCondition(key : String, condition : ASelectableCondition) {
        elementConditions[key] = condition
        onIntegralConditionChanged()
    }

    fun removeElementCondition(key: String) {
        if(elementConditions.containsKey(key)) {
            elementConditions.remove(key)
            onIntegralConditionChanged()
        }
    }

    // events ********************************************************************************************
    private val filteringListeners = arrayListOf<IGtdFilteringListener>()

    fun addFilteringListener(l: IGtdFilteringListener) {
        filteringListeners.add(l)
    }

    private fun fireBeforeFilteringComposed() {
        filteringListeners.forEach {
            it.onBeforeGtdFilteringComposed()
        }
    }

    // heap ********************************************************************************************
    private val filterController get() = GtdController.filterController

    private val frCondition : ASelectableCondition?
        get() =
            if(filterController.isFilterActive)
                filterController.selectedCondition
            else null
}