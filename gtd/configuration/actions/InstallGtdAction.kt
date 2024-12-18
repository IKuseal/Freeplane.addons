package org.freeplane.features.gtd.configuration.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.core.ui.menubuilders.generic.UserRole
import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.actions.GTD_IS_ACTIVE_PROP
import org.freeplane.features.gtd.actions.GTD_IS_INSTALLED_PROP
import org.freeplane.features.gtd.configuration.GtdConfigurationController
import org.freeplane.features.gtd.configuration.isGtdInstalled
import org.freeplane.features.gtd.core.GtdController
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class InstallGtdAction() : AGtdAction("Gtd.InstallGtdAction") {
    init {
        unregisterEnabledProp(GTD_IS_ACTIVE_PROP)
        registerDisabledProp(GTD_IS_INSTALLED_PROP)
    }

    override fun actionPerformed(e: ActionEvent?) {
        val map = GtdController.currentMap
        GtdConfigurationController.installGtd(map)
        setSelected()
    }

    override fun setSelected() {
        isSelected = GtdController.currentMapSafe?.isGtdInstalled ?: false
    }

    override fun setEnabled(prop: String?, enabled: Boolean, userRole: UserRole?) {
        super.setEnabled(prop, enabled, userRole)
        setSelected()
    }
}