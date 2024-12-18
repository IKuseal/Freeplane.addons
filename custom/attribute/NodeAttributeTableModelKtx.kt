package org.freeplane.features.custom.attribute

import org.freeplane.features.attribute.NodeAttributeTableModel
import org.freeplane.features.map.NodeModel

fun NodeAttributeTableModel.containsAttribute(name : String) =
    getAttributeIndex(name) != -1

val NodeModel.attributes : NodeAttributeTableModel
    get() = NodeAttributeTableModel.getModel(this)