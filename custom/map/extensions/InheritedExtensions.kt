package org.freeplane.features.custom.map.extensions

import org.freeplane.core.extension.IExtension
import kotlin.reflect.KClass

@JvmInline
value class InheritedExtensions(
    val container : MutableMap<KClass<out IExtension>, InheritedExtensionsOfType<IExtension>> = hashMapOf()) {

    fun clear() = container.clear()

    fun removeInheritedOfType(clazz: KClass<out IExtension>) = container.remove(clazz)

    operator fun get(clazz: KClass<out IExtension>) = container[clazz]

    fun createInheritedOfType(clazz: KClass<out IExtension>) =
        (this[clazz] ?: InheritedExtensionsOfType<IExtension>().also { container[clazz] = it })
}