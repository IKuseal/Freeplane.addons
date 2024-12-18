package org.freeplane.features.logistics.delivery

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.logistics.destination.Destination
import org.freeplane.features.logistics.search.DestinationSearchModel
import org.freeplane.features.map.NodeModel
import org.freeplane.view.swing.features.custom.centered
import org.freeplane.view.swing.features.custom.logistics.DeliverDialog
import java.awt.event.ActionEvent

class DeliverAction(key : String, model : () -> DestinationSearchModel) : ADeliverAction("Logistics.DeliverAction.$key", model) {
    lateinit var dialog : DeliverDialog

    override lateinit var nodesToDeliver : Collection<NodeModel>

    override fun actionPerformed(e: ActionEvent?) {
        super.actionPerformed(e)

        nodesToDeliver = GlobalFrFacade.selectedNodes

        dialog = DeliverDialog(
            GlobalFrFacade.frame,
            _model,
            deliverParamsModel
        ).apply {
            pack()
            centered()
        }

        dialog.show()
    }

    override fun beforeDeliver(destination: Destination) {
        dialog.run {
            isVisible = false
            dispose()
        }
    }

    override fun afterDeliver() {}
}