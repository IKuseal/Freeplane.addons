package org.freeplane.features.custom.historynavigation.edithistorynavigation

import org.freeplane.features.custom.historynavigation.ArgsHistoryIterator
import org.freeplane.features.custom.historynavigation.HistoryDestination
import org.freeplane.features.custom.historynavigation.HistoryNavigationArgs
import org.freeplane.features.map.NodeModel
import java.util.*

class EditHistoryNavigator {
    private val history: LinkedList<HistoryDestination> = LinkedList()
    private var numChanges = 0

    fun add(node : NodeModel) {
        history.push(HistoryDestination(node))
    }

    fun iterator() : ArgsHistoryIterator = this.EditIterator()

    inner class EditIterator : ArgsHistoryIterator {
        private var numChangesWhenCreated = numChanges
        private val historyIterator = history.listIterator(history.lastIndex)

        override val isValid: Boolean
            get() = numChanges == numChangesWhenCreated

        override fun forward(args: HistoryNavigationArgs): NodeModel? {
            TODO("Not yet implemented")
        }

        override fun forward(): NodeModel? {
            TODO("Not yet implemented")
        }

        override fun back(args: HistoryNavigationArgs): NodeModel? {
            TODO("Not yet implemented")
        }

        override fun back(): NodeModel? {
            if(!isValid) throw IllegalStateException()

            return historyIterator.previousNode()
        }
    }

    private fun Iterator<HistoryDestination>.nextNode() : NodeModel? {
        var node : NodeModel? = null

        while(node == null && hasNext()) {
            node = next().node
        }

        return node
    }

    private fun ListIterator<HistoryDestination>.previousNode() : NodeModel? {
        var node : NodeModel? = null

        while(node == null && hasPrevious()) {
            node = previous().node
        }

        return node
    }
}