package org.freeplane.features.gtd.actions

import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.currentCover
import java.awt.event.ActionEvent
import javax.swing.ImageIcon

abstract class AMultipleGtdNodeCoverAction : AMultipleGtdNodeAction {
    constructor(key: String) : super(key)
    constructor(key: String, name: String?, imageIcon: ImageIcon? = null) : super(key, name, imageIcon)

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeModel) {
        actionPerformed(e, node.currentCover)
    }

    abstract fun actionPerformed(e: ActionEvent?, node: GtdNodeCover)
}