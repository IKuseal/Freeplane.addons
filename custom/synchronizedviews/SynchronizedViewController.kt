package org.freeplane.features.custom.synchronizedviews

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.views
import org.freeplane.features.map.INodeSelectionListener
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.NodeModel
import org.freeplane.view.swing.map.MapView
import javax.swing.SwingUtilities

object SynchronizedViewController : INodeSelectionListener {
    fun init() {
        GlobalFrFacade.mapController.addNodeSelectionListener(this)

        GlobalFrFacade.run {
            addAction(SwitchViewIsSynchronizedAction())
        }
    }

    var isSynchronizationInProcess = false

    var selectedNode : NodeModel? = null;

    override fun onSelect(node: NodeModel) {
        if(isSynchronizationInProcess) return

        val view = GlobalFrFacade.currentMapView
            .takeIf { it.isSynchronized } ?: return

        handleSynchronized(view, node)
    }

    private fun handleSynchronized(currentView: MapView, selectedNode : NodeModel) {
        isSynchronizationInProcess = true

        this.selectedNode = selectedNode;

        val synchronizedViews = currentView.model.synchronizedViews

        synchronizedViews
            .filter { it !== currentView }
            .forEach {
                changeToMapView(it)
            }

        returnToViewInWork(currentView)

        isSynchronizationInProcess = false
    }

    private fun returnToViewInWork(mapView: MapView) {
        changeToMapView(mapView)
    }

    private fun changeToMapView(mapView: MapView) {
        GlobalFrFacade.mapViewController.changeToMapView(mapView)
    }

    fun processAfterViewChange() {
        if(isSynchronizationInProcess) {
            GlobalFrFacade.selectNode(selectedNode!!)
        }
    }
}

val MapModel.synchronizedViews : Collection<MapView>
    get() = views.filter { it.isSynchronized }