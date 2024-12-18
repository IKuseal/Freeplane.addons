package org.freeplane.features.gtd.map.cover

import org.freeplane.features.custom.map.extensions.ExtensionInheritanceController
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.core.gtdEventProp
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.map.GtdMap
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.map.cover.actions.LightResetGtdNodeAction
import org.freeplane.features.gtd.map.cover.actions.*
import org.freeplane.features.gtd.map.cover.event.GtdMapCoverChangeAnnouncer
import org.freeplane.features.gtd.map.cover.event.IGtdMapCoverChangeAnnouncer
import org.freeplane.features.gtd.map.event.GtdMapChangeEvent
import org.freeplane.features.gtd.map.event.GtdNodeChangeEvent
import org.freeplane.features.gtd.map.event.IGtdMapChangeListener
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.node.currentGtdNodeCover
import org.freeplane.features.gtd.node.forEach
import org.freeplane.features.map.IMapChangeListener
import org.freeplane.features.map.IMapLifeCycleListener
import org.freeplane.features.map.MapChangeEvent
import org.freeplane.features.map.MapModel
import org.freeplane.features.ui.IMapViewChangeListener
import org.freeplane.view.swing.features.custom.gtd.planesorder.ShowGtdTagsViewOrderDialogAction
import org.freeplane.view.swing.map.MapView
import java.awt.Component

object GtdMapCoverController : IGtdMapChangeListener, IMapLifeCycleListener, IMapChangeListener, IMapViewChangeListener,
    IGtdMapCoverChangeAnnouncer by GtdMapCoverChangeAnnouncer
{
    object DelayedFreeplaneNotifier {
        private var isSet = false

        fun notify(map: MapModel, prop : GtdEventProp) {
            if(isSet) return

            isSet = true

            GtdController.invokeLater {
                GtdController.fireMapChanged(GtdMapCoverController::class.java, map, prop, setsDirtyFlag = false)
                isSet = false
            }
        }
    }

    val gtdNodeCoverEventProp = GtdEventProp(gtdEventProp)

    val gtdMapCoverEventProp = GtdEventProp(gtdEventProp)

    val gtdMapCoverUndefinedEventProp = GtdEventProp(gtdMapCoverEventProp)

    val gtdMapCoverDataUpdatedProp = GtdEventProp(gtdMapCoverEventProp)

    val gtdMapCoverUpdatedEventProp = GtdEventProp(gtdMapCoverEventProp)
    val gtdMapCoverUpdatedMainEventProp = GtdEventProp(gtdMapCoverUpdatedEventProp)

    fun init() {
        GtdController.addMapLifecycleListener(this)
        GtdController.addMapChangeListener(this)
        GtdMapController.addMapChangeListener(this)

        initActions()
    }

    private fun initActions() {
        GtdController.run {
            addAction(ResetAllIntentToTopAction())
            addAction(ShowGtdTagsViewOrderDialogAction())
            addAction(LightResetGtdNodeAction())
            addAction(SwitchIsTotalCoverageModeAction())
            addAction(SwitchUseTotalCoverageModeDefaultStyleAction())
            addAction(SetTotalCoverageModeStateAction())
            addAction(SetTotalCoverageModeGcsTypeAction())
            addAction(GtdIntentToTopDialogAction())
        }
    }

    val currentGtdMapCover get() = GtdController.currentMap.currentGtdMapCover

    // map cover change ********************************************************************************
    // listening on changes in GtdMapModel ***************************************************************
    override fun afterViewChange(oldView: Component?, newView: Component?) {
        if(newView == null) return

        newView as MapView

        updateMap(newView.mapCover.gtdMapCover)
    }

    override fun onCreate(map: MapModel) {
        updateMap(map.currentGtdMapCover)
    }

    override fun mapChanged(event: MapChangeEvent?) {
        val prop = event?.property ?: return

        if(prop == ExtensionInheritanceController.evenProp) updateMap(event.map.currentGtdMapCover)
    }

    override fun onMapNodeChange(event: GtdNodeChangeEvent) {
        if(DelayedMapCoverUpdater.isCalculationSet) return
        DelayedMapCoverUpdater.updateMap(event.node.gtdMap.currentCover)
    }

    override fun onMapStructureChange(map: GtdMap) {
        DelayedMapCoverUpdater.updateMap(map.currentCover)
    }

    override fun onMapChange(event: GtdMapChangeEvent) {
        DelayedMapCoverUpdater.updateMap(event.map.currentCover)
    }

    // firing map cover changes ****************************************************************************
    override fun fireGtdMapCoverChange(map: GtdMapCover, prop : GtdEventProp) {
        if(prop.iz(gtdMapCoverDataUpdatedProp)) updateMap(map)

        fireGtdMapCoverChangeInternal(map, prop)
    }

    private fun fireGtdMapCoverChangeInternal(map: GtdMapCover, prop : GtdEventProp) {
        GtdMapCoverChangeAnnouncer.fireGtdMapCoverChange(map, prop)

        if(prop.iz(gtdMapCoverUpdatedEventProp))
            DelayedFreeplaneNotifier.notify(map.mapCover.map, gtdMapCoverUpdatedEventProp)
        // TODO(W) other also should be fired for enablers at least?
    }

    // update map ***************************************************************************************
    fun updateMap(map : GtdMapCover) {
        map.root.forEach(action = GtdNodeCover::invalidate)

        fireGtdMapCoverChangeInternal(map, gtdMapCoverUpdatedMainEventProp)
    }

    fun updateNode(node : GtdNodeCover) {
        node.invalidate()

        GtdNodeCoverChangeAnnouncer.announce(node)
    }

    object GtdNodeCoverChangeAnnouncer {
        private var isSet = false

        fun announce(node: GtdNodeCover) {
            if(isSet) return

            isSet = true

            GtdController.invokeLater {
                fireGtdMapCoverChangeInternal(node.gtdMapCover, gtdMapCoverUpdatedMainEventProp)
                isSet = false
            }
        }
    }

    // editing nodes ****************************************************************************************
    fun resetNode(gtdNodeC : GtdNodeCover) {
        GtdCoverIntentController.clearIntents(gtdNodeC)
    }

    // intent to top ***************************************************************************************
    fun setIntentToTop(gtdNodeCover: GtdNodeCover, intent : GtdIntent?) {
        gtdNodeCover.intentToTop = intent
        updateNode(gtdNodeCover)
    }

    fun resetAllIntentToTop(gtdMapCover: GtdMapCover) {
        gtdMapCover.root.forEach { setIntentToTop(this, null) }
    }

    // planes managing ****************************************************************************************
    fun swapTagsByViewOrder(map : GtdMapCover, index1 : Int, index2 : Int) {
        map.tagsViewOrderAccessor.swap(index1, index2)
        fireGtdMapCoverChange(map, gtdMapCoverDataUpdatedProp)
    }

    fun switchTotalCoverageModeAction(map : GtdMapCover) {
        map.isTotalCoverageMode = !map.isTotalCoverageMode
        fireGtdMapCoverChange(map, gtdMapCoverDataUpdatedProp)
    }

    fun switchUseTotalCoverageModeDefaultStyle(map : GtdMapCover) {
        map.useTotalCoverageModeDefaultStyle = !map.useTotalCoverageModeDefaultStyle
        fireGtdMapCoverChange(map, gtdMapCoverDataUpdatedProp)
    }

    fun setTotalCoverageModeState(map : GtdMapCover, gtdState: GtdState?) {
        map.totalCoverageModeState = gtdState
        fireGtdMapCoverChange(map, gtdMapCoverDataUpdatedProp)
    }

    fun setTotalCoverageModeGcsType(map : GtdMapCover, gcsType: GcsType?) {
        map.totalCoverageModeGcsType = gcsType
        fireGtdMapCoverChange(map, gtdMapCoverDataUpdatedProp)
    }

    // heap ***************************************************************************************************
    val currentMapCover get() = GtdController.currentMap.currentGtdMapCover
    val currentGtdMapCoverSafe get() = GtdController.currentMapSafe?.currentGtdMapCover

    val selectedGtdNodeCover get() = GtdController.selectedNode.currentGtdNodeCover
    val selectedGtdNodeCoverSafe get() = GtdController.selectedNodeSafe?.currentGtdNodeCover
}