package org.freeplane.features.custom.map

import org.freeplane.core.extension.ExtensionContainer
import org.freeplane.core.extension.IExtension
import org.freeplane.features.custom.map.extensions.InheritedExtensions
import org.freeplane.features.custom.pinneddata.IPinnedDataContainer
import org.freeplane.features.custom.pinneddata.PinnedDataContainer
import org.freeplane.features.map.NodeModel
import org.freeplane.view.swing.map.NodeView
import kotlin.reflect.KClass

class NodeCover(
    val node : NodeModel,
    val mapCover : MapCover
) : IPinnedDataContainer by PinnedDataContainer() {

    val extensions = ExtensionContainer(hashMapOf())

    val inheritedExtensions : InheritedExtensions by lazy { InheritedExtensions() }

    // tree ****************************************************************************************************
    val parent : NodeCover? get() = node.parentNode?.createNodeCover(mapCover)

    val children : Collection<NodeCover> get() = node.children!!.map { it.createNodeCover(mapCover) }

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
}

val NodeView.createNodeCover : NodeCover get() =
    model!!.createNodeCover(map!!.mapCover)