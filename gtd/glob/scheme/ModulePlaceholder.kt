package org.freeplane.features.gtd.glob.scheme

import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.icon.IconContainedCondition
import org.freeplane.features.map.NodeModel

class ModulePlaceholder(val node : NodeModel) {
    val moduleUri = "${GtdController.getLink(node)!!.uri}"

    val id get() = moduleUri

    val mapUri get() = moduleUri.split("#")[0]

    val isDisabled = disabledCondition.checkNode(node)

    companion object {
        val disabledCondition = IconContainedCondition("button_cancel")
    }
}