package org.freeplane.features.custom.map

import org.freeplane.core.extension.ExtensionContainer
import org.freeplane.core.extension.IExtension
import org.freeplane.features.map.MapModel
import kotlin.reflect.KClass

class MapCover(val map : MapModel) {
    var name = ++baseId

    val extensions = ExtensionContainer(hashMapOf())

    val root : NodeCover get() = map.rootNode.createNodeCover(this)

    // extensions ***********************************************************************************************
    fun getExtensionNoCast(clazz : KClass<out IExtension>) : IExtension? =
        extensions.getExtensionNoCast(clazz.java)

    fun <T : IExtension> getExtension(clazz: KClass<T>) : T? =
        getExtensionNoCast(clazz) as T?

    fun containsExtension(clazz: KClass<out IExtension>) = getExtensionNoCast(clazz) != null

    fun putExtension(clazz : KClass<out IExtension>, extension : IExtension?) : IExtension? =
        extensions.putExtension(clazz.java, extension)

    fun putExtension(extension : IExtension) : IExtension? =
        extensions.putExtension(extension)

    companion object {
        var baseId = 0;
    }
}