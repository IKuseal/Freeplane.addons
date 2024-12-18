package org.freeplane.features.gtd.actions

import org.freeplane.core.ui.AFreeplaneAction
import javax.swing.ImageIcon

abstract class AGtdAction : AFreeplaneAction {

    constructor(key: String) : super(key)
    constructor(key: String, name : String? = null, imageIcon: ImageIcon? = null) : super(key, name, imageIcon)

    init {
        registerEnabledProp(GTD_IS_ACTIVE_PROP)
    }

    override fun setEnabled() {
        isEnabled = isAllPropsEnabled
    }

}