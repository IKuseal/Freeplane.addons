package org.freeplane.features.custom.historynavigation.edithistorynavigation

import org.freeplane.features.custom.GlobalFrFacade

object EditHistoryNavigationController {
    val navigator = EditHistoryNavigator()

    fun init() {
        GlobalFrFacade.run {
            addAction(EditHistoryBackAction())
        }
    }


}