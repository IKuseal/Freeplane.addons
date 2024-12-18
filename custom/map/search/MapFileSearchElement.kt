package org.freeplane.features.custom.map.search

import org.freeplane.features.custom.space.MapFile

data class MapFileSearchElement(val mapFile : MapFile, val index : String) {
    override fun toString() = index
}