package org.freeplane.features.custom.filter.selectiveexclusion

import org.freeplane.core.extension.IExtension
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.MapCover
import org.freeplane.features.custom.map.NodeCover
import org.freeplane.features.custom.map.stream
import org.freeplane.features.custom.treestream.forEachSpread

object FilterSelectiveExclusionController {
    private object Extension : IExtension
    private object BranchExclusionFlag
    private object ExclusionFlag

    fun init() {
        GlobalFrFacade.run {
            addAction(SwitchBranchSelectiveExclusionFlagAction())
        }
    }

    // ************************************************************************************
    var MapCover.isSelectiveExclusionEnabled
        get() = getExtension(Extension::class) != null
        set(value) {
            if(true) putExtension(Extension)
            else putExtension(Extension::class, null)
        }

    fun MapCover.refreshSelectiveExclusionFiltering() {
        root.stream().forEachSpread(root.exclusionFlag) { it, spread ->
            val exclusionFlag = spread || it.branchExclusionFlag

            it.exclusionFlag = exclusionFlag

            exclusionFlag
        }
    }

    // ************************************************************************************
    var NodeCover.branchExclusionFlag
        get() = getPinnedData<Any>(BranchExclusionFlag) != null
        set(value) {
            if(value) setPinnedData(BranchExclusionFlag, true)
            else setPinnedData(BranchExclusionFlag, null)
            mapCover.refreshFiltering()
        }

    // ************************************************************************************
    var NodeCover.exclusionFlag
        get() = getPinnedData<Any>(ExclusionFlag) != null
        private set(value) {
            if(value) setPinnedData(ExclusionFlag, true)
            else setPinnedData(ExclusionFlag, null)
        }

    // ************************************************************************************
    private fun MapCover.refreshFiltering() {
        isSelectiveExclusionEnabled = true
        GlobalFrFacade.filterController.myApplyFilter(true)
    }
}