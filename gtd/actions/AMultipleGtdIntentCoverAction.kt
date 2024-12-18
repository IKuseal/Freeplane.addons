package org.freeplane.features.gtd.actions

import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent
import javax.swing.ImageIcon

abstract class AMultipleGtdIntentCoverAction : AMultipleGtdNodeCoverAction {
    constructor(key: String) : super(key)
    constructor(key: String, name: String?, imageIcon: ImageIcon? = null) : super(key, name, imageIcon)

    init {
        registerEnabledProp(GTD_SELECTED_INTENT_EXISTS_PROP)
    }

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        node.intent?.let { actionPerformed(e, node, it) }
    }

    abstract fun actionPerformed(e: ActionEvent?, node: GtdNodeCover, intentC : GtdNodeCover.GtdIntentCover)
}