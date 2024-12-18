package org.freeplane.features.custom.map.extensions

import org.freeplane.core.extension.IExtension

@JvmInline
value class InheritedExtensionsOfType<T : IExtension>(val list : MutableList<T> = arrayListOf()) {
    fun removeChildInherited() {
        list.takeIf { it.isNotEmpty() && it.last().getTreeSpread()!! == TreeSpread.CHILD }
            ?.removeLast()
    }

    fun add(extension : T) = list.add(extension)

    fun addAll(list : List<T>) {
        this.list.addAll(list)
    }

    val isEmpty get() = list.isEmpty()

    fun clear() = list.clear()

    fun copy() = InheritedExtensionsOfType<T>().also { it.addAll(list) }
}