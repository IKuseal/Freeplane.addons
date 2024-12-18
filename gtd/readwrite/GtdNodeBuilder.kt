package org.freeplane.features.gtd.readwrite

import org.freeplane.core.extension.IExtension
import org.freeplane.core.io.*
import org.freeplane.features.custom.map.FakeMapModel
import org.freeplane.features.custom.map.extensions.AllWrapperExtensionsBuilder
import org.freeplane.features.gtd.configuration.isGtdInstalled
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.map.NodeModel
import org.freeplane.n3.nanoxml.XMLElement
import java.io.IOException

object GtdNodeBuilder : IExtensionElementWriter, IElementDOMHandler {
    const val TAG = "gtdNode"

    fun registerBy(reader: ReadManager, writer: WriteManager) {
        writer.addExtensionElementWriter(GtdNodeModel::class.java, this)

        reader.addElementHandler(TAG, this)

        GtdIntentBuilder.registerBy(reader)
        AllWrapperExtensionsBuilder.run {
            add(SpreadGtdStateBuilder)
            add(SpreadGtdSubStateBuilder)
        }
        GtdModuleBuilder.registerBy(reader)
        GtdLayerBuilder.registerBy(reader)
    }

    @Throws(IOException::class)
    override fun writeContent(writer: ITreeWriter, element: Any?, extension: IExtension) {
        element as NodeModel

        if(!element.map.isGtdInstalled) {
            return
        }

        val gtdNode = extension as GtdNodeModel

        if(gtdNode.isEmpty) return

        val extXml = XMLElement()

        extXml.name = TAG

        gtdNode.intents.forEach {GtdIntentBuilder.writeIntent(extXml, it)}

        GtdModuleBuilder.writeContent(extXml, gtdNode)
        GtdLayerBuilder.writeContent(extXml, gtdNode)

        writer.addElement(gtdNode, extXml)
    }

    override fun createElement(parent: Any?, tag: String?, attributes: XMLElement?): Any? {
        parent as NodeModel

        if(parent.map.isGtdInstalled) return parent.gtdNode
        else return FakeMapModel.usualFakeNode.gtdNode
    }

    override fun endElement(parent: Any?, tag: String?, userObject: Any?, dom: XMLElement?) {
    }
}
