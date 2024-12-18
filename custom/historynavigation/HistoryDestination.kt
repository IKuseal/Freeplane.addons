package org.freeplane.features.custom.historynavigation

import org.freeplane.features.map.NodeModel
import java.lang.ref.WeakReference

class HistoryDestination(node : NodeModel) {
    private val _node : WeakReference<NodeModel> = WeakReference(node)

    val node : NodeModel? get() = _node.get()


}