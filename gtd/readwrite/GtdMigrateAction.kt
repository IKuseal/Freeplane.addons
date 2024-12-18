package org.freeplane.features.gtd.readwrite

import org.freeplane.core.ui.AFreeplaneAction
import java.awt.event.ActionEvent

class GtdMigrateAction : AFreeplaneAction("Gtd.GtdMigrateAction") {
    override fun actionPerformed(e: ActionEvent?) {
        GtdMigrationController.migrate()
    }

}