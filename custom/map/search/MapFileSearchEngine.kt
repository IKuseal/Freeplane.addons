package org.freeplane.features.custom.map.search

import org.freeplane.features.custom.space.MapFile

object MapFileSearchEngine {
    fun createMapFileSearchElement(mapFile : MapFile, indexStrategy : (MapFile) -> String) =
        MapFileSearchElement(mapFile, indexStrategy(mapFile))
}

val standardMapFileIndexStrategy : (MapFile) -> String = {it.file.nameWithoutExtension}