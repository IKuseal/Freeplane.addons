package org.freeplane.features.gtd.readwrite

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.readwrite.standardUpToStartedConductor
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.gtd.allmaps.gtdInstalledMaps
import org.freeplane.features.gtd.core.GtdController

object GtdMigrationController {
    fun init() {
        GtdController.run {
            addAction(GtdMigrateAction())
        }
    }

    fun migrate() {
        SpaceController.gtdInstalledMaps.forEach {
            val map = standardUpToStartedConductor(it.file).doJob()
            GlobalFrFacade.save(map)
        }
    }
}