package org.freeplane.features.gtd.readwrite

import org.freeplane.core.extension.IExtension
import org.freeplane.core.io.ITreeWriter
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.custom.map.extensions.WrapperExtensionBuilder
import org.freeplane.features.gtd.configuration.isGtdInstalled
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.GtdSubState
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.map.NodeModel

object SpreadGtdSubStateBuilder : WrapperExtensionBuilder<GtdSubState>() {
    override val tag: String get() = "spreadSubState"
    override val key: Any get() = GtdSubState::class

    override fun writeContent(writer: ITreeWriter, element: Any?, extension: IExtension) {
        val node = element as NodeModel
        if(node.map.isGtdInstalled) super.writeContent(writer, element, extension)
    }

    override fun writeValue(value: GtdSubState): String = value.name

    override fun readValue(value: String) = GtdSubState.valueOf(value)

    override fun setValue(parent: Any, value: GtdSubState, spread: TreeSpread) {
        parent as NodeModel
        if(parent.map.isGtdInstalled) {
            parent.gtdNode.setSpreadSubState(value, spread)
        }
    }
}