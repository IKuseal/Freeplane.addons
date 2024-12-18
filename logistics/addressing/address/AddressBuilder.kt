package org.freeplane.features.logistics.addressing.address

import org.freeplane.core.extension.IExtension
import org.freeplane.core.io.*
import org.freeplane.features.map.NodeModel
import org.freeplane.n3.nanoxml.XMLElement
import java.io.IOException


object AddressBuilder : IExtensionElementWriter, IElementDOMHandler {
    const val TAG = "logisticsAddress"

    fun registerBy(reader: ReadManager, writer: WriteManager) {
        writer.addExtensionElementWriter(Address::class.java, this)
        reader.addElementHandler(TAG, this)
    }

    @Throws(IOException::class)
    override fun writeContent(writer: ITreeWriter, element: Any?, extension: IExtension) {
        val extXml = XMLElement()
        extXml.name = TAG
        writer.addElement(extension, extXml)
    }

    override fun createElement(parent: Any?, tag: String?, attributes: XMLElement?): Any? {
        return (parent as NodeModel).createLogisticsAddress()
    }

    override fun endElement(parent: Any?, tag: String?, userObject: Any?, dom: XMLElement?) {
    }
}