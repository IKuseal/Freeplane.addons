package org.freeplane.features.custom.map.extensions

import org.freeplane.core.extension.IExtension
import org.freeplane.core.io.IExtensionElementWriter
import org.freeplane.core.io.ITreeWriter
import org.freeplane.features.custom.GlobalFrFacade

object AllWrapperExtensionsBuilder : IExtensionElementWriter {
    init {
        GlobalFrFacade.mapController.writeManager.addExtensionElementWriter(WrapperExtension::class.java, this)
    }

    private val builders = hashMapOf<Any, WrapperExtensionBuilder<*>>()

    fun add(builder : WrapperExtensionBuilder<*>) {
        builders[builder.key] = builder
        GlobalFrFacade.mapController.run {
            readManager.addElementHandler(builder.tag, builder)
            builder.registerAttributeHandlers(readManager)
        }
    }

    override fun writeContent(writer: ITreeWriter, element: Any?, extension: IExtension) {
        val key = (extension as WrapperExtension<*>).key
        builders[key]?.writeContent(writer, element, extension)
    }
}