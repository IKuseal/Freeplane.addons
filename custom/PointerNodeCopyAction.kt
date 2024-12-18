package org.freeplane.features.custom

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.custom.map.FakeMapModel
import org.freeplane.features.custom.map.fileNameFromId
import org.freeplane.features.custom.map.setRelativeLinkToNode
import org.freeplane.features.custom.synchronizednodes.syncNodeExtension
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.clipboard.MapClipboardController
import java.awt.event.ActionEvent

class PointerNodeCopyAction : AFreeplaneAction("Custom.PointerNodeCopyAction") {
    override fun actionPerformed(e: ActionEvent?) {
        val nodes = GlobalFrFacade.selectedNodes

        val copyNodes = nodes.map(::pointerCopy)

        val clipboardController = GlobalFrFacade.modeController.getExtension(MapClipboardController::class.java)

        clipboardController.run {
            val copyContent = copySingle(copyNodes)
            setClipboardContents(copyContent)
        }
    }

    private fun pointerCopy(node : NodeModel) : NodeModel {
        val copy = FakeMapModel.newNode(node.userObject)

        if(node.syncNodeExtension == null) {
            copy.setRelativeLinkToNode(node)
        }
        else {
            val ext = node.syncNodeExtension!!
            copy.setRelativeLinkToNode(ext.mapId.fileNameFromId, ext.nodeId)
        }

        return copy
    }
}