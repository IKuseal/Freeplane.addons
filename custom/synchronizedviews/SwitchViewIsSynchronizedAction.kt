package org.freeplane.features.custom.synchronizedviews

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.custom.GlobalFrFacade
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchViewIsSynchronizedAction : AFreeplaneAction("SynchronizedViews.SwitchViewIsSynchronizedAction") {
    override fun actionPerformed(e: ActionEvent?) {
        val mapView = GlobalFrFacade.currentMapView
        mapView.isSynchronized = !mapView.isSynchronized
    }

    override fun setSelected() {
        isSelected = GlobalFrFacade.currentMapViewSafe?.isSynchronized ?: false
    }
}