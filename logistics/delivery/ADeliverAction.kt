package org.freeplane.features.logistics.delivery

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.space.isResumed
import org.freeplane.features.logistics.destination.Destination
import org.freeplane.features.logistics.navigation.NavigationController
import org.freeplane.features.logistics.search.DestinationSearchModel
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.mindmapmode.MMapModel
import org.freeplane.view.swing.features.custom.logistics.DeliverParamsModel
import java.awt.event.ActionEvent

abstract class ADeliverAction(key : String, val model : () -> DestinationSearchModel) : AFreeplaneAction(key) {
    protected lateinit var deliverParamsModel : DeliverParamsModel
    protected lateinit var _model : DestinationSearchModel

    override fun actionPerformed(e: ActionEvent?) {
        deliverParamsModel = DeliverParamsModel(
            DeliveryController.toNavigateToDestination,
            NavigationController.toNavigateToDestinationInNewView
        )

        _model = model()
        _model.onResult = ::onDeliver
    }

    private fun onDeliver(destination : Destination) {
        // before
        beforeDeliver(destination)

        // deliver
        val destinationNode = destination.node
        val destinationMap = destinationNode.map as MMapModel

        val deliveredNodes = DeliveryController.deliver(nodesToDeliver, destinationNode)

        // after
        afterDeliver()

        // end
        if(!destinationMap.isResumed) GlobalFrFacade.save(destinationMap)

        if(deliverParamsModel.toNavigateAfter)
            NavigationController.navigate(deliveredNodes.first(), deliverParamsModel.toNavigateInNewView)
    }

    protected abstract fun beforeDeliver(destination: Destination)
    protected abstract fun afterDeliver()
    protected abstract val nodesToDeliver : Collection<NodeModel>

}