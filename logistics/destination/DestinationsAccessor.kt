package org.freeplane.features.logistics.destination

import org.freeplane.features.custom.map.forEachWithSpread
import org.freeplane.features.logistics.addressing.address.Address
import org.freeplane.features.logistics.addressing.address.logisticsAddress
import org.freeplane.features.logistics.addressing.scope.Scope
import org.freeplane.features.logistics.addressing.scope.logisticsScope
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.NodeModel

class DestinationsAccessor(val map : MapModel) {
    fun all() : Collection<Destination> {

        val destinations = arrayListOf<Destination>().also { it.add(map.destination) }

        map.rootNode.forEachWithSpread(arrayListOf<Scope>()) {parentPath ->
            val path = ArrayList<Scope>(parentPath)

            updatePath(this, path)

            logisticsAddress?.let {
                normalizeAddress(it, path)
                destinations.add(Destination(this, it))
            }

            path
        }

        return destinations
    }

    private fun normalizeAddress(address : Address, path : List<Scope>) {
        if(path.isNotEmpty()) address.path = ArrayList(path)
    }

    private fun updatePath(node : NodeModel, path : MutableList<Scope>) {
        node.logisticsScope?.let {
            path.add(it)
        }
    }
}