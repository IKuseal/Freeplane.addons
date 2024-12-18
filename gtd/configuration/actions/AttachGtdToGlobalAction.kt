package org.freeplane.features.gtd.configuration.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.core.ui.menubuilders.generic.UserRole
import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.actions.GTD_IS_ACTIVE_PROP
import org.freeplane.features.gtd.actions.GTD_IS_INSTALLED_PROP
import org.freeplane.features.gtd.configuration.GtdConfigurationController
import org.freeplane.features.gtd.configuration.gtdConfiguration
import org.freeplane.features.gtd.core.GtdController
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class AttachGtdToGlobalAction() : AGtdAction("Gtd.AttachGtdToGlobalAction") {
    init {
        unregisterEnabledProp(GTD_IS_ACTIVE_PROP)
        registerEnabledProp(GTD_IS_INSTALLED_PROP)
    }

    override fun actionPerformed(e: ActionEvent?) {
        GtdConfigurationController.attachGtdToGlobal(GtdController.currentMap, !isAttached())
    }

    override fun setSelected() {
        isSelected = isAttached()
    }

    private fun isAttached() =
        GtdController.currentMapSafe?.gtdConfiguration?.attachedToGlobal ?: false

    override fun setEnabled(prop: String?, enabled: Boolean, userRole: UserRole?) {
        super.setEnabled(prop, enabled, userRole)
        setSelected()
    }
}