package org.freeplane.features.gtd.readwrite

import org.freeplane.core.io.IElementDOMHandler
import org.freeplane.core.io.ReadManager
import org.freeplane.features.custom.map.extensions.SpreadBuilder
import org.freeplane.features.gtd.layers.*
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.n3.nanoxml.XMLElement
import java.io.IOException

object GtdLayerBuilder : IElementDOMHandler {
    const val TAG = "gtdLayer"
    private const val LAYER_TYPE = "layerType"
    const val GCS_TYPE = "gcsType"

    private const val EXCLUDE_FLAG_TAG = "layerExcludeFlag"

    fun registerBy(reader: ReadManager) {
        reader.addElementHandler(TAG, this)
        reader.addElementHandler(EXCLUDE_FLAG_TAG, this)
        registerAttributeHandlers(reader)
    }

    @Throws(IOException::class)
    fun writeContent(parentXml : XMLElement, gtdNode : GtdNodeModel) {
        gtdNode.layerSpreadsMap.map.values.forEach {
            writeLayer(parentXml, it)
        }

        writeExcludeFlag(parentXml, gtdNode)
    }

    fun writeLayer(parentXml: XMLElement, gtdLayer : GtdLayer) {
        val xmlElm = XMLElement()

        xmlElm.name = TAG

        xmlElm.setAttribute(LAYER_TYPE, gtdLayer.type.name)
        xmlElm.setAttribute(GCS_TYPE, gtdLayer.gcsType.name)
        SpreadBuilder.writeSpread(xmlElm, gtdLayer)

        parentXml.addChild(xmlElm)
    }

    fun writeExcludeFlag(parentXml: XMLElement, gtdNode: GtdNodeModel) {
        if(!gtdNode.layerExcludeFlag) return

        val xmlElm = XMLElement()
        xmlElm.name = EXCLUDE_FLAG_TAG
        parentXml.addChild(xmlElm)
    }

    override fun createElement(parent: Any?, tag: String, attributes: XMLElement?) = when(tag) {
        TAG -> GtdLayer()
        EXCLUDE_FLAG_TAG -> {
            (parent as GtdNodeModel).layerExcludeFlag = true
            null
        }
        else -> null
    }

    override fun endElement(parent: Any?, tag: String, userObject: Any?, dom: XMLElement?) {
        when(tag) {
            TAG -> {
                val gtdNode = parent as GtdNodeModel
                val layer = userObject as GtdLayer
                gtdNode.setLayer(layer, layer.spread)
            }
        }
    }

    private fun registerAttributeHandlers(reader: ReadManager) {
        reader.addAttributeHandler(TAG, LAYER_TYPE) { userObject, value ->
            (userObject as GtdLayer).type = GtdLayerType.valueOf(value)
        }

        reader.addAttributeHandler(TAG, GCS_TYPE) { userObject, value ->
            (userObject as GtdLayer).gcsType = GcsType.valueOf(value)
        }

        reader.addAttributeHandler(TAG, SpreadBuilder.ATTR) { userObject, value ->
            (userObject as GtdLayer).spread = SpreadBuilder.readSpread(value)
        }
    }
}