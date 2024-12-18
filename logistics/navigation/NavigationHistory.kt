package org.freeplane.features.logistics.navigation

import org.freeplane.features.map.NodeModel

// TODO() возможно, удалить
object NavigationHistory {
    private val stack = ArrayDeque<NodeModel>(100)
    private var iterator = stack.listIterator()
    private const val sizeLimit = 100;

    fun add(destination : NodeModel) {


        if(stack.size >= sizeLimit) stack.clear()


        TODO()
    }

    private fun clearHead() {
        val startIndex = iterator.nextIndex()
    }

//    fun hasNext() : Boolean {
//        TODO()
//    }
//
//    fun hasPrevious() : Boolean {
//        TODO()
//    }

    fun next() : NodeModel? {
        TODO()
    }

    fun previous() : NodeModel? {
        TODO()
    }

    private fun validate(destination: NodeModel) : Boolean {
        TODO()
    }

    private fun iterator(isForward : Boolean) : MutableIterator<NodeModel> =
        if(isForward)
            object : MutableIterator<NodeModel> {
                override fun hasNext() = iterator.hasNext()

                override fun next(): NodeModel = iterator.next()

                override fun remove() = iterator.remove()
            }
        else
            object : MutableIterator<NodeModel> {
                override fun hasNext(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun next(): NodeModel {
                    TODO("Not yet implemented")
                }

                override fun remove() {
                    TODO("Not yet implemented")
                }
            }
}