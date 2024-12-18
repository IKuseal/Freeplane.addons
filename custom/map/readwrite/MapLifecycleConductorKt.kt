package org.freeplane.features.custom.map.readwrite

import org.freeplane.features.custom.map.MapLifecycleState
import org.freeplane.features.map.mindmapmode.MMapModel
import org.freeplane.features.url.mindmapmode.MapLifecycleConductor
import java.io.File

fun updationToResumeConductor(map : MMapModel) =
    MapLifecycleConductor().apply {
        update(map)
        targetState = MapLifecycleState.RESUMED
    }

fun standardUpToStartedConductor(file : File) =
    MapLifecycleConductor().apply {
        load(file)
        targetState = MapLifecycleState.STARTED
        useCash()
        saveAfterLoading()
    }

fun standardUpToResumedConductor(file : File) =
    MapLifecycleConductor().apply {
        load(file)
        targetState = MapLifecycleState.RESUMED
        useCash()
    }

//fun standardNotToSaveUpToStartedConductor(file : File) =
//    MapLifecycleConductor().apply {
//        load(file)
//        targetState = MapLifecycleState.STARTED
//        useCash()
//    }