package org.freeplane.features.logistics.destination

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.name
import org.freeplane.features.custom.space.SpaceArea
import org.freeplane.features.custom.space.SpaceArea.*
import org.freeplane.features.custom.space.WorkSpace
import org.freeplane.features.custom.space.isCashed
import org.freeplane.features.map.MapModel

object DestinationsController {
    fun availableDestinations(area : SpaceArea, map : MapModel? = null) : Collection<Destination> = arrayListOf<Destination>().apply {
        val maps = arrayListOf<MapModel>().apply {
            when(area) {
                WORKSPACE -> addAll(WorkSpace.maps)
                CASHED -> addAll(WorkSpace.cashed)
                CURRENT_MAP -> add(GlobalFrFacade.currentMap)
                SPECIFIC_MAP -> {
                    if(!map!!.isCashed) throw IllegalArgumentException()
                    add(map)
                }
            }
        }.filter {
            it.name != "PlaceholderMap"
        }

        maps.forEach {
            addAll(availableDestinationsInternal(it))
        }
    }

    private fun availableDestinationsInternal(map: MapModel) : Collection<Destination> = arrayListOf<Destination>().apply {
        addAll(DestinationsAccessor(map).all())
    }
}