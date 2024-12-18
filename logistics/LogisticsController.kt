package org.freeplane.features.logistics

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.space.isResumed
import org.freeplane.features.logistics.addressing.AddressController
import org.freeplane.features.logistics.addressing.address.Address
import org.freeplane.features.logistics.addressing.address.logisticsAddress
import org.freeplane.features.logistics.addressing.scope.Scope
import org.freeplane.features.logistics.addressing.scope.logisticsScope
import org.freeplane.features.logistics.collector.CollectorController
import org.freeplane.features.logistics.delivery.DeliveryController
import org.freeplane.features.logistics.navigation.NavigationController
import org.freeplane.features.logistics.specialnodes.MailNodeAction
import org.freeplane.features.map.NodeModel

object LogisticsController {
    fun init() {
        DeliveryController.init()
        AddressController.init()
        NavigationController.init()
        CollectorController.init()

        GlobalFrFacade.run {
            addAction(MailNodeAction())
            addAction(SetFixedScopeAction())
            addAction(SetFixedAddressAction())
            mapController.addUIMapChangeListener(DeliveryController)
        }
    }

    var fixedScopeNode : NodeModel? = null
        get() =
            if(field?.map?.isResumed ?: false) field
            else {
                if(field != null) fixedScopeNode = null
                null
            }

    val fixedScope : Scope? get() = fixedScopeNode?.logisticsScope

    var fixedAddressNode : NodeModel? = null
        get() =
            if(field?.map?.isResumed ?: false) field
            else {
                if(field != null) fixedAddressNode = null
                null
            }

    val fixedAddress : Address? get() = fixedAddressNode?.logisticsAddress
}