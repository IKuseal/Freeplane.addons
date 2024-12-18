package org.freeplane.features.gtd.readwrite

import org.freeplane.core.io.IElementDOMHandler
import org.freeplane.core.io.ReadManager
import org.freeplane.features.gtd.module.GtdModule
import org.freeplane.features.gtd.module.setIdWith
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.n3.nanoxml.XMLElement
import java.io.IOException

object GtdModuleBuilder : IElementDOMHandler {
    const val TAG = "gtdModule"
    private const val LAYER0_TO_EXPORT = "LAYER0_TO_EXPORT"
    private const val LAYER1_TO_EXPORT = "LAYER1_TO_EXPORT"

    fun registerBy(reader: ReadManager) {
        reader.addElementHandler(TAG, this)
        registerAttributeHandlers(reader)
    }

    @Throws(IOException::class)
    fun writeContent(parentXml : XMLElement, gtdNode : GtdNodeModel) {
        val module = gtdNode.module ?: return

        val xmlElm = XMLElement()

        xmlElm.name = TAG

        xmlElm.setAttribute(LAYER0_TO_EXPORT, module.layer0ToExport.toString())
        xmlElm.setAttribute(LAYER1_TO_EXPORT, module.layer1ToExport.toString())

        parentXml.addChild(xmlElm)
    }

    override fun createElement(parent: Any?, tag: String?, attributes: XMLElement?): Any? {
        parent as GtdNodeModel
        return GtdModule().also {
            parent.module = it
            it.setIdWith(parent)
        }
    }

    override fun endElement(parent: Any?, tag: String?, userObject: Any?, dom: XMLElement?) {
    }

    private fun registerAttributeHandlers(reader: ReadManager) {
        reader.addAttributeHandler(TAG, LAYER0_TO_EXPORT) { userObject, value ->
            (userObject as GtdModule).layer0ToExport = value.toBoolean()
        }

        reader.addAttributeHandler(TAG, LAYER1_TO_EXPORT) { userObject, value ->
            (userObject as GtdModule).layer1ToExport = value.toBoolean()
        }
    }
}