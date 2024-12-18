package org.freeplane.features.custom.map.extensions

import org.freeplane.core.extension.IExtension
import org.freeplane.core.io.IElementDOMHandler
import org.freeplane.core.io.IExtensionElementWriter
import org.freeplane.core.io.ITreeWriter
import org.freeplane.core.io.ReadManager
import org.freeplane.n3.nanoxml.XMLElement

abstract class WrapperExtensionBuilder<T : Any> : IExtensionElementWriter, IElementDOMHandler {
    abstract val tag : String
    abstract val key : Any

    lateinit var value : T
    lateinit var spread: TreeSpread

    override fun writeContent(writer: ITreeWriter, element: Any?, extension: IExtension) {
        writeValue(writer, element, extension, (extension as WrapperExtension<T>).value)
    }

    fun writeValue(writer: ITreeWriter, element: Any?, extension: IExtension, value: T) {
        val extXml = XMLElement()

        extXml.name = tag

        SpreadBuilder.writeSpread(extXml, extension)
        extXml.setAttribute(VALUE, writeValue(value))

        writer.addElement(value, extXml)
    }

    override fun createElement(parent: Any, tag: String?, attributes: XMLElement?) = parent

    override fun endElement(parent: Any, tag: String?, element: Any?, dom: XMLElement?) {
        setValue(parent, value, spread)
    }

    fun registerAttributeHandlers(reader: ReadManager) {
        reader.addAttributeHandler(tag, VALUE)
        { userObject, value -> this.value = readValue(value) }

        reader.addAttributeHandler(tag, SpreadBuilder.ATTR)
        { userObject, value -> this.spread = SpreadBuilder.readSpread(value) }
    }

    abstract fun writeValue(value : T) : String

    abstract fun readValue(value : String) : T

    abstract fun setValue(parent : Any, value: T, spread: TreeSpread)

    companion object {
        private const val VALUE = "value"
    }
}