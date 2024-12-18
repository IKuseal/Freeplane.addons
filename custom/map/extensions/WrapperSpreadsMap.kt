package org.freeplane.features.custom.map.extensions

import org.freeplane.core.extension.IExtension
import kotlin.reflect.KClass

class WrapperSpreadsMap<T : Any>(clazz : KClass<out IExtension>,
                                 map: MutableMap<TreeSpread, WrapperExtension<T>> = hashMapOf())
    : SpreadsMap<WrapperExtension<T>>(clazz, map)
{
     constructor(spreadsMap : SpreadsMap<WrapperExtension<T>>) : this(spreadsMap.clazz, spreadsMap.map)

    fun getValue(spread : TreeSpread) : T? = this[spread]?.value

    fun setValue(spread: TreeSpread, value : T?) {
        if(value == null) this[spread] = null
        else this[spread] = WrapperExtension(value)
    }

    override fun copy() = WrapperSpreadsMap<T>(clazz, HashMap(map))
}