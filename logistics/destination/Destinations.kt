package org.freeplane.features.logistics.destination

import org.freeplane.features.custom.map.name
import org.freeplane.features.logistics.addressing.address.Address
import org.freeplane.features.map.MapModel

val MapModel.destination : Destination get() =
    Destination(
        rootNode,
        Address(rootNode).apply {
            nameStrategy = { "!! ${this@destination.name}" }
        }
    )