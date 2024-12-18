package org.freeplane.features.gtd.intent.actions

import org.freeplane.features.custom.actions.ANodeChangeActionsEnabler
import org.freeplane.features.gtd.actions.GTD_SELECTED_INTENT_EXISTS_PROP
import org.freeplane.features.gtd.intent.GtdCoverIntentController

object GtdSelectedIntentExistsEnabler : ANodeChangeActionsEnabler(GTD_SELECTED_INTENT_EXISTS_PROP) {
    override fun checkEnabled() = GtdCoverIntentController.selectedIntent != null
}