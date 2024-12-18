package org.freeplane.features.gtd.views

import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.views.gcsview.*

object GtdViewsController : IGcsViewController by GcsViewController {
    fun init() {
        GcsViewController.init()

        GtdController.run {
            addUiBuilder(ShowGcsViewMenuBuilder.key, ShowGcsViewMenuBuilder)
            addAction(HideGcsViewAction())
            addAction(SwitchGcsViewAction())
        }
    }
}