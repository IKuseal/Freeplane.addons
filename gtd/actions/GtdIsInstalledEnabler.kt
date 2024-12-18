package org.freeplane.features.gtd.actions

import org.freeplane.features.custom.actions.AMapChangeActionsEnabler
import org.freeplane.features.gtd.configuration.isGtdInstalled
import org.freeplane.features.gtd.core.GtdController

object GtdIsInstalledEnabler : AMapChangeActionsEnabler(GTD_IS_INSTALLED_PROP) {
    override fun checkEnabled() = GtdController.currentMapSafe?.isGtdInstalled ?: false
}