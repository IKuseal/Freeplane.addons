package org.freeplane.features.gtd.actions

import org.freeplane.features.custom.actions.ANodeChangeActionsEnabler
import org.freeplane.features.gtd.module.GtdModuleController.selectedGtdModuleSafe

object GtdIsModuleExistEnabler : ANodeChangeActionsEnabler(GTD_IS_MODULE_EXIST_PROP) {
    override fun checkEnabled() = selectedGtdModuleSafe != null
}