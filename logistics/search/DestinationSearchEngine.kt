package org.freeplane.features.logistics.search

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.name
import org.freeplane.features.logistics.addressing.address.Address
import org.freeplane.features.logistics.addressing.scope.Scope
import org.freeplane.features.logistics.destination.Destination
import org.freeplane.features.map.MapModel

object DestinationSearchEngine {
    fun createDestinationSearchElement(destination : Destination,
                                       indexStrategy : (Destination) -> String) =
        DestinationSearchElement(destination, indexStrategy(destination))
}

const val SCCS = " :: "

val MapModel.mapPrefix get() =
    if(this == GlobalFrFacade.currentMap) "%"
    else name

val List<Scope>.standardPathToString get() = joinToString(SCCS) { it.name }

fun standardFullPathToString(map : MapModel, path : List<Scope>) : String {
    var result = map.mapPrefix
    if(path.isNotEmpty()) result+= SCCS + path.standardPathToString

    return result
}

val standardDestinationIndexStrategy : (Destination) -> String = {
    standardAddressToString(it.address)
}

fun standardAddressToString(address : Address) : String {
    val ADDRESS_PREFIX = "!"

    return standardFullPathToString(address.node.map, address.path) +
            " $ADDRESS_PREFIX " + address.name
}