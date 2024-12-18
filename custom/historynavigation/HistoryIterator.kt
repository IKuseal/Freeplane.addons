package org.freeplane.features.custom.historynavigation

import org.freeplane.features.map.NodeModel

interface HistoryIterator {
    val isValid : Boolean

    fun forward() : NodeModel?
    fun back() : NodeModel?
}