package org.freeplane.features.logistics.addressing

import org.freeplane.core.undo.IActor
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.logistics.addressing.address.AddressBuilder
import org.freeplane.features.logistics.addressing.address.SwitchLogisticsAddressAction
import org.freeplane.features.logistics.addressing.address.createLogisticsAddress
import org.freeplane.features.logistics.addressing.address.logisticsAddress
import org.freeplane.features.logistics.addressing.scope.ScopeBuilder
import org.freeplane.features.logistics.addressing.scope.SwitchLogisticsScopeAction
import org.freeplane.features.logistics.addressing.scope.createLogisticsScope
import org.freeplane.features.logistics.addressing.scope.logisticsScope
import org.freeplane.features.map.NodeModel

object AddressController {
    const val LOGISTICS_SCOPE_EVENT_PROP = "LOGISTICS_SCOPE_EVENT_PROP"

    const val LOGISTICS_ADDRESS_EVENT_PROP = "LOGISTICS_ADDRESS_EVENT_PROP"

    fun init() {
        GlobalFrFacade.run {
            addAction(SwitchLogisticsScopeAction())
            addAction(SwitchLogisticsAddressAction())

            mapController.run {
                ScopeBuilder.registerBy(readManager, writeManager)
                AddressBuilder.registerBy(readManager, writeManager)
            }
        }
    }

    fun switchScope(node : NodeModel, enabled : Boolean) {
        if(enabled == (node.logisticsScope != null)) return

        val oldVal = node.logisticsScope

        val actor = object : IActor {
            override fun act() {
                if(enabled) node.createLogisticsScope() else node.logisticsScope = null
                GlobalFrFacade.nodeChanged(node, LOGISTICS_SCOPE_EVENT_PROP)
            }

            override fun getDescription() = ""

            override fun undo() {
                if(!enabled) node.logisticsScope = oldVal else node.logisticsScope = null
                GlobalFrFacade.nodeChanged(node, LOGISTICS_SCOPE_EVENT_PROP)
            }
        }

        execute(actor, node)
    }

    fun switchAddress(node : NodeModel, enabled : Boolean) {
        if(enabled == (node.logisticsAddress != null)) return

        val oldVal = node.logisticsAddress

        val actor = object : IActor {
            override fun act() {
                if(enabled) node.createLogisticsAddress() else node.logisticsAddress = null
                GlobalFrFacade.nodeChanged(node, LOGISTICS_ADDRESS_EVENT_PROP)
            }

            override fun getDescription() = ""

            override fun undo() {
                if(!enabled) node.logisticsAddress = oldVal else node.logisticsAddress = null
                GlobalFrFacade.nodeChanged(node, LOGISTICS_ADDRESS_EVENT_PROP)
            }
        }

        execute(actor, node)
    }

    // heap ***********************************************************************************************
    private fun execute(actor : IActor, node : NodeModel) {
        GlobalFrFacade.modeController.execute(actor, node.map)
    }
}