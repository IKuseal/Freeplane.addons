package org.freeplane.features.custom.historynavigation

import org.freeplane.features.map.NodeModel

interface ArgsHistoryIterator : HistoryIterator {
    fun forward(args : HistoryNavigationArgs) : NodeModel?
    fun back(args : HistoryNavigationArgs) : NodeModel?
}