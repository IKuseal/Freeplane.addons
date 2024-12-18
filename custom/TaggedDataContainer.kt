package org.freeplane.features.custom

// TODO("to remove?")
class TaggedDataContainer(private val tagSupplier: () -> Int) {
    private val container : MutableMap<Int, Any> = hashMapOf()

    fun generateTag() = tagSupplier()

    operator fun set(tag : Int, data : Any?) {
        if(data != null) container[tag] = data
        else container.remove(tag)
    }

    operator fun get(tag: Int) = container[tag]
}