package org.freeplane.features.gtd.actions

import org.freeplane.features.custom.actions.AMapChangeActionsEnabler
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.glob.GtdGlobController

object GtdIsGlobMapSelectedEnabler : AMapChangeActionsEnabler(GTD_IS_GLOB_MAP_SELECTED_PROP) {
    override fun checkEnabled() =
        GtdController.currentMapSafe?.file?.nameWithoutExtension == GtdGlobController.SCHEME_MAP_ID
}