package org.freeplane.features.custom.map.extensions

import org.freeplane.core.extension.IExtension
import org.freeplane.features.custom.map.extensions.TreeSpread.*
import kotlin.reflect.KClass

open class SpreadsMap <T : IExtension> (val clazz : KClass<out IExtension>,
                                        val map : MutableMap<TreeSpread, T> = hashMapOf())
{
    operator fun set(spread : TreeSpread, ext: T?) {
        if(ext == null) map.remove(spread)
        else {
            when(spread) {
                OWN -> {
                    map[OWN] = ext
                }
                CHILD -> {
                    map[CHILD] = ext
                    map.remove(DESCENDANT)
                }
                DESCENDANT -> {
                    map[DESCENDANT] = ext
                    map.remove(CHILD)
                }
                else -> {}
            }
        }
    }

    operator fun get(spread : TreeSpread) = map[spread]

    open fun copy() = SpreadsMap<T>(clazz, HashMap(map))

    val isEmpty get() = map.isEmpty()
}