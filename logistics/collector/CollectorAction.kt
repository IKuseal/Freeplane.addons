package org.freeplane.features.logistics.collector

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.logistics.delivery.ADeliverAction
import org.freeplane.features.logistics.destination.Destination
import org.freeplane.features.logistics.search.DestinationSearchModel
import org.freeplane.features.map.NodeModel
import org.freeplane.view.swing.features.custom.collector.CollectorDataModel
import org.freeplane.view.swing.features.custom.collector.CollectorDialog
import java.awt.event.ActionEvent

class CollectorAction(
    key : String,
    model : () -> DestinationSearchModel
) : ADeliverAction("Logistics.CollectorAction.$key", model) {

    lateinit var dialog : CollectorDialog
    private lateinit var collectorDataModel : CollectorDataModel
    private lateinit var destinationNode : NodeModel

    override fun actionPerformed(e: ActionEvent?) {
        super.actionPerformed(e)

        val data = DataToCollectAccessor.getDataAsString()
        val link = CollectorController.linkToCollect

        collectorDataModel = CollectorDataModel(data, link)

        dialog = CollectorDialog(
            collectorDataModel,
            _model,
            deliverParamsModel
        ).apply {
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }
    }

    override val nodesToDeliver: Collection<NodeModel>
        get() = listOf(
            CollectorController.createDataNode(destinationNode.map).also {
                it.text = collectorDataModel.data
                collectorDataModel.link.takeIf { it.isNotEmpty() }?.let { link ->
                    GlobalFrFacade.setLink(it, link)
                }
            }
        )

    override fun beforeDeliver(destination: Destination) {
        dialog.run {
            isVisible = false
            dispose()
        }

        destinationNode = destination.node
    }

    override fun afterDeliver() {
    }
}