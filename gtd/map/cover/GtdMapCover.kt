package org.freeplane.features.gtd.map.cover

import org.freeplane.core.extension.IExtension
import org.freeplane.features.custom.map.MapCover
import org.freeplane.features.custom.map.currentMapCover
import org.freeplane.features.custom.pinneddata.IPinnedDataContainer
import org.freeplane.features.custom.pinneddata.PinnedDataContainer
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.filter.GtdFilterData
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.map.GtdMap
import org.freeplane.features.gtd.map.gtdMap
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.node.gtdNodeCover
import org.freeplane.features.gtd.tag.workBuiltinTags
import org.freeplane.features.gtd.transparency.GtdTransparencyData
import org.freeplane.features.map.MapModel

class GtdMapCover(
    val mapCover: MapCover
) : IPinnedDataContainer by PinnedDataContainer(), IExtension {

    val root : GtdNodeCover get() = mapCover.root.gtdNodeCover
    val model : GtdMap get() = mapCover.map.gtdMap

    // * * *
    val tagCreationParamsAccessor = GtdTagCreationParamsAccessor(workBuiltinTags.toSet())

    // heap **********************************************************************************
    var transparencyData: GtdTransparencyData? = null

    var filterData: GtdFilterData? = null

    var doShowLayers : Boolean = true

    // coverage-mode *********************************************************
    var isTotalCoverageMode = false
    var useTotalCoverageModeDefaultStyle = true
    var totalCoverageModeState : GtdState? = null
    var totalCoverageModeGcsType : GcsType? = null
    val totalCoverModeParamsNotSet get() =
        totalCoverageModeState == null
            && totalCoverageModeGcsType == null
}

val MapModel.currentGtdMapCover : GtdMapCover
    get() = currentMapCover.gtdMapCover

var MapCover.gtdMapCover : GtdMapCover
    get() = getExtension(GtdMapCover::class) ?: GtdMapCover(this).also { gtdMapCover = it }
    set(value) {
        putExtension(value)
    }

val GtdMap.currentCover get() = map.currentGtdMapCover