package org.freeplane.features.logistics.specialnodes

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.map.NodeModel

val NodeModel.mailNode : NodeModel?
    get() = children.find { it.isMailNode }

val NodeModel.isMailNode get() = text == "@"

fun NodeModel.createMailNode() =
    mailNode ?: NodeModel("@", map).also {
        GlobalFrFacade.mapController.insertNode(it, this)
    }

val NodeModel.mailNode2 : NodeModel?
    get() = children.find { it.text == "@@" }

fun NodeModel.createMailNode2() =
    mailNode2 ?: NodeModel("@@", map).also {
        GlobalFrFacade.mapController.insertNode(it, this)
    }