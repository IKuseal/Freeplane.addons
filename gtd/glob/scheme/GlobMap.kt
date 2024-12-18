package org.freeplane.features.gtd.glob.scheme

import org.freeplane.features.gtd.map.gtdMap
import org.freeplane.features.gtd.module.GtdModuleController
import org.freeplane.features.map.MapModel

@JvmInline
value class GlobMap(val map: MapModel) {
    val modulePlaceholders : Collection<ModulePlaceholder>
        get() = GtdModuleController.findNodesWithModules(map.gtdMap).map(::ModulePlaceholder)

    val activeModulePlaceholders
        get() = modulePlaceholders.filterNot { it.isDisabled }
}