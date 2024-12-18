package org.freeplane.features.custom.space

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.MapLifecycleState
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.mindmapmode.MMapModel
import java.net.URL

object WorkSpace {
    private val cash : MutableSet<MMapModel> = mutableSetOf()

    // check ******************************************************************************************
    fun isCashedWithUrl(url : URL) = getCashed(url) != null

    fun isCashed(map : MapModel) = cash.contains(map)

    // access ******************************************************************************************
    val maps : Collection<MMapModel>
        get() = cashed.filter { it.isResumed }

    val cashed : Collection<MMapModel>
        get() = cash

    fun getCashed(url : URL) : MMapModel? {
        cash.forEach {
            if(GlobalFrFacade.sameFile(it.url, url)) return it
        }
        return null
    }

    // modifying ******************************************************************************************
    fun addToCash(map: MMapModel) {
        cash.add(map)
    }

    fun removeFromCash(map : MapModel) {
        cash.remove(map)
    }
}

fun isMapResumedWith(url : URL) = GlobalFrFacade.mapViewController.checkIfFileIsAlreadyOpened(url)

val MapModel.isResumed get() = lifecycleState == MapLifecycleState.RESUMED

val MapModel.isCashed get() = WorkSpace.isCashed(this)