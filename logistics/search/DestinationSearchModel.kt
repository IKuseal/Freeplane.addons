package org.freeplane.features.logistics.search

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.readwrite.standardUpToStartedConductor
import org.freeplane.features.custom.space.SpaceArea
import org.freeplane.features.custom.space.SpaceArea.*
import org.freeplane.features.custom.space.MapFile
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.logistics.addressing.address.Address
import org.freeplane.features.logistics.addressing.scope.Scope
import org.freeplane.features.logistics.destination.Destination
import org.freeplane.features.logistics.destination.DestinationsController
import org.freeplane.features.map.MapModel
import org.freeplane.view.swing.features.custom.logistics.destinationsearch.IDestinationSearchModel

class DestinationSearchModel() : IDestinationSearchModel {
    override val destinations: Collection<Destination>
        get() = when(searchArea) {
            CURRENT_MAP -> _destinations(GlobalFrFacade.currentMap)
            SPECIFIC_MAP -> {
                if(specificMap == null) arrayListOf()
                else _destinations(standardUpToStartedConductor(specificMap!!.file).doJob())
            }
            else -> _destinations()
        }

    private fun _destinations(map : MapModel? = null) =
        DestinationsController.availableDestinations(searchArea, map)

    override val mapFiles: Collection<MapFile>
        get() = SpaceController.maps

    override var searchArea: SpaceArea = WORKSPACE

    override var specificMap: MapFile? = null

    override var result: Destination? = null
        set(value) {
            field = value
            if(value != null) onResult?.let { it(value) }
        }

    var onResult : ((Destination) -> Unit)? = null

    override var fixedScope : (() -> Scope?)? = null

    override var fixedAddress : (() -> Address?)? = null

    override fun reset() {
        specificMap = null
        result = null
    }
}