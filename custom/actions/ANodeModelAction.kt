package org.freeplane.features.custom.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent
import javax.swing.ImageIcon

abstract class ANodeModelAction : AFreeplaneAction {
    constructor(key: String) : super(key)
    constructor(key: String, name : String? = null, imageIcon: ImageIcon? = null) : super(key, name, imageIcon)

    override fun actionPerformed(e: ActionEvent?) {
        actionPerformed(e, GlobalFrFacade.selectedNodeSafe ?: return)
    }

    abstract fun actionPerformed(e : ActionEvent?, node : NodeModel)

    override fun setEnabled() {
        isEnabled = isAllPropsEnabled
    }
}