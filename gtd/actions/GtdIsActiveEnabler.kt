package org.freeplane.features.gtd.actions

import org.freeplane.features.custom.actions.AMapChangeActionsEnabler
import org.freeplane.features.gtd.configuration.isGtdActive
import org.freeplane.features.gtd.core.GtdController

object GtdIsActiveEnabler : AMapChangeActionsEnabler(GTD_IS_ACTIVE_PROP) {
    override fun checkEnabled() = GtdController.currentMapSafe?.isGtdActive ?: false
}