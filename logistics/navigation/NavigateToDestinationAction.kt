package org.freeplane.features.logistics.navigation

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.logistics.destination.Destination
import org.freeplane.features.logistics.search.DestinationSearchModel
import org.freeplane.view.swing.features.custom.centered
import org.freeplane.view.swing.features.custom.logistics.NavigateToDestinationDialog
import java.awt.event.ActionEvent

class NavigateToDestinationAction(key : String, val model : () -> DestinationSearchModel) : AFreeplaneAction("Logistics.NavigateToDestinationAction.$key") {
    lateinit var dialog : NavigateToDestinationDialog

    override fun actionPerformed(e: ActionEvent?) {

        dialog = NavigateToDestinationDialog(
            GlobalFrFacade.frame,
            model().apply { onResult = ::onResult },
            NavigationController.toNavigateToDestinationInNewView
        ).apply {
            pack()
            centered()
        }

        dialog.show()
    }

    private fun onResult(destination : Destination) {

        dialog.run {
            isVisible = false
            dispose()
        }

        val destinationNode = destination.node
        val navigateInNewView = dialog.toNavigateInNewView

        NavigationController.navigate(destinationNode, navigateInNewView)
    }
}