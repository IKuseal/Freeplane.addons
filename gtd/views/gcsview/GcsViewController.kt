package org.freeplane.features.gtd.views.gcsview

import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.GtdStateClass
import org.freeplane.features.gtd.data.elements.GtdSubState
import org.freeplane.features.gtd.data.elements.GtdSubStateClass
import org.freeplane.features.gtd.filter.GtdFilterController
import org.freeplane.features.gtd.filter.events.IGtdFilteringListener
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.layers.Gcs
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.node.forEach
import org.freeplane.features.gtd.tag.GTD_PLANE_FAKE
import org.freeplane.features.gtd.tag.GTD_TAG_GCS_EXCLUDED
import org.freeplane.features.gtd.tag.GtdPlane
import org.freeplane.features.map.NodeModel

object GcsViewController : IGcsViewController, IGtdFilteringListener {
//    val GCS_EXCLUDE_INTENT = GtdIntent()

    object GcsExcludeIntent : GtdIntent(GtdStateClass.F.default, listOf(GTD_TAG_GCS_EXCLUDED)) {
        override var plane: GtdPlane
            get() = super.plane
            set(value) {}

        override var state: GtdState
            get() = GtdStateClass.F.default
            set(value) {}

        override var gcsType: GcsType
            get() = GcsType.LOCAL
            set(value) {}

        override val subStates: Collection<GtdSubState> get() = listOf()
        override fun getSubState(subStateClass: GtdSubStateClass) = null
        override fun putSubState(subState: GtdSubState) = null
        override fun removeSubState(subStateClass: GtdSubStateClass) = null
        override fun containsSubState(subStateClass: GtdSubStateClass) = false

//        override val isEmpty = true

        override fun copy(): GtdIntent {
            return this
        }
    }

    private class ConditionProxy(val condition : ASelectableCondition) : ASelectableCondition() {
        override fun checkNode(node: NodeModel): Boolean {
            val viewData = viewData!!
            if(viewData.updateRequires) {
                updateGcs()

                viewData.updateRequires = false
                GtdController.invokeLater {
                    viewData.updateRequires = true
                }
            }
            return condition.checkNode(node)
        }

        override fun createDescription() = condition.toString()

        override fun getName() = " "
    }

    fun init() {
    }

    private val key : String
        get() = this::class.simpleName!!


    private var viewData : GcsViewData?
        get() = currentGtdMapC.getPinnedData(key)
        set(value) {
            currentGtdMapC.setPinnedData(key, value)
        }

    private val gcs : Gcs? get() = viewData?.gcs

    override val isGcsViewShown get() = viewData != null

    // show/hide ***********************************************************************************************
    override fun showGcsView(gcs: Gcs) {
        releaseResources()

        if(!prepareGcs(gcs)) {
            gcs.invalidate()
            return
        }

        viewData = GcsViewData(gcs)

        enableFiltering(createCondition())
    }

    private fun prepareGcs(gcs : Gcs) =
        gcs.takeIf { it.isValid() }
//            ?.let { true }
            ?.let { calculateGcsView(gcs); true }
            ?: false

    override fun hideGcsView() {
        releaseResources()
    }

    private fun releaseResources() {
        if(isGcsViewShown) {
            disableFiltering()

            clearGcsExcludeIntent()

            gcs!!.invalidate()
            viewData = null
        }
    }

    private fun clearGcsExcludeIntent() {
        gcs!!.root.forEach {
            if(intentToTop == GcsExcludeIntent) {
                intentToTop = null
                GtdMapCoverController.updateNode(this)
            }
        }
    }

    // condition ***********************************************************************************************
    private fun createCondition() = ConditionProxy(viewData!!.gcs.condition)

    // filtering ****************************************************************************************************
    private fun enableFiltering(condition : ASelectableCondition) {
        GtdFilterController.addElementCondition(key, condition)
        GtdFilterController.onIntegralConditionChanged()
    }

    private fun disableFiltering() {
        GtdFilterController.removeElementCondition(key)
        GtdFilterController.onIntegralConditionChanged()
    }

    override fun onBeforeGtdFilteringComposed() {
        if(isGcsViewShown && !gcs!!.isValid()) {
            releaseResources()
        }
    }

    // gcs-updating *********************************************************************************************************
    private fun updateGcs() {
        if(!gcs!!.isValid()) {
            releaseResources()
        }

        calculateGcsView(gcs!!)
    }

    private fun calculateGcsView(gcs : Gcs) {
        gcs.calculate()
        gcs.root.forEach {
            if(!gcs.isIncludedOf(this)) {
                this.intentToTop = GcsExcludeIntent
                GtdMapCoverController.updateNode(this)
            }
            else if(this.intentToTop == GcsExcludeIntent) {
                this.intentToTop = null
                GtdMapCoverController.updateNode(this)
            }
        }
    }

    // heap ****************************************************************************************************
    private val currentGtdMapC get() = GtdMapCoverController.currentGtdMapCover
}