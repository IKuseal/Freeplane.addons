package org.freeplane.features.gtd.configuration

import org.freeplane.core.extension.IExtension
import org.freeplane.core.io.*
import org.freeplane.features.map.MapModel
import org.freeplane.n3.nanoxml.XMLElement
import java.io.IOException


object GtdConfigurationBuilder : IExtensionElementWriter, IElementDOMHandler {
    const val TAG = "gtdConfiguration"
    const val GTD_ENABLED_ATTR = "GTD_ENABLED_ATTR"
    const val ATTACHED_TO_GLOB = "GTD_ATTACHED_TO_GLOBAL"


    fun registerBy(reader: ReadManager, writer: WriteManager) {
        writer.addExtensionElementWriter(GtdConfiguration::class.java, this)
        reader.addElementHandler(TAG, this)
        registerAttributeHandlers(reader)
    }

    @Throws(IOException::class)
    override fun writeContent(writer: ITreeWriter, element: Any?, extension: IExtension) {
        val gtdConfig = extension as GtdConfiguration
        val extXml = XMLElement()
        extXml.name = TAG
        extXml.setAttribute(GTD_ENABLED_ATTR, gtdConfig.gtdEnabled.toString())
        extXml.setAttribute(ATTACHED_TO_GLOB, gtdConfig.attachedToGlobal.toString())
        writer.addElement(gtdConfig, extXml)
    }

    override fun createElement(parent: Any?, tag: String?, attributes: XMLElement?): Any? {
        return (parent as MapModel).createGtdConfiguration()
    }

    override fun endElement(parent: Any?, tag: String?, userObject: Any?, dom: XMLElement?) {
    }

    private fun registerAttributeHandlers(reader: ReadManager) {
        reader.addAttributeHandler(
            TAG, GTD_ENABLED_ATTR
        ) { userObject, value -> (userObject as GtdConfiguration).gtdEnabled = value.toBoolean() }
        reader.addAttributeHandler(
            TAG, ATTACHED_TO_GLOB
        ) { userObject, value -> (userObject as GtdConfiguration).attachedToGlobal = value.toBoolean() }
    }
}