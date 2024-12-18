package org.freeplane.features.gtd.readwrite

import org.freeplane.core.extension.IExtension
import org.freeplane.core.io.ITreeWriter
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.custom.map.extensions.WrapperExtensionBuilder
import org.freeplane.features.custom.map.name
import org.freeplane.features.gtd.configuration.isGtdInstalled
import org.freeplane.features.gtd.data.elements.GtdNum
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.GtdStateClass
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.map.NodeModel

object SpreadGtdStateBuilder : WrapperExtensionBuilder<GtdState>() {
    override val tag: String get() = "spreadState"
    override val key: Any get() = GtdState::class

    override fun writeContent(writer: ITreeWriter, element: Any?, extension: IExtension) {
        val node = element as NodeModel
        if(node.map.isGtdInstalled) super.writeContent(writer, element, extension)
    }

    override fun writeValue(value: GtdState): String = value.name

    override fun readValue(value: String) = mmMigrateState(value)

    private fun mmMigrateState(key : String) : GtdState =
        when(key) {
            "R:NO" -> GtdStateClass.R(GtdNum.S5)
            else -> GtdState.valueOf(key)
        }

    override fun setValue(parent: Any, value: GtdState, spread: TreeSpread) {
        parent as NodeModel

        if(parent.map.isGtdInstalled)
            parent.gtdNode.setSpreadState(value, spread)
    }
}