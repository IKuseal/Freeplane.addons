package org.freeplane.features.logistics.navigation

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.readwrite.updationToResumeConductor
import org.freeplane.features.custom.space.SpaceArea.*
import org.freeplane.features.custom.space.isResumed
import org.freeplane.features.logistics.LogisticsController
import org.freeplane.features.logistics.delivery.DeliverAction
import org.freeplane.features.logistics.search.DestinationSearchModel
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.mindmapmode.MMapModel

object NavigationController {
    fun init() {
        GlobalFrFacade.run {
            addAction(SwitchToNavigateToDestinationInNewViewAction())

            val cm = ::DestinationSearchModel
            addAction(NavigateToDestinationAction("CURRENT_MAP") { cm().apply { searchArea = CURRENT_MAP } })
            addAction(NavigateToDestinationAction("SPECIFIC_MAP") { cm().apply { searchArea = SPECIFIC_MAP } })
            addAction(NavigateToDestinationAction("WORKSPACE") { cm().apply { searchArea = WORKSPACE } })
            addAction(NavigateToDestinationAction("CASHED") { cm().apply { searchArea = CASHED } })
            addAction(NavigateToDestinationAction("SCOPE") {
                cm().apply {
                    searchArea = WORKSPACE
                    fixedScope = LogisticsController::fixedScope::get
                }
            })
            addAction(NavigateToDestinationAction("Address") {
                cm().apply {
                    searchArea = WORKSPACE
                    fixedAddress = LogisticsController::fixedAddress::get
                }
            })
        }
    }

    var toNavigateToDestinationInNewView = false

    fun navigate(destination: NodeModel, inNewView : Boolean = false) {
        val destinationMap = destination.map as MMapModel

        if(!destinationMap.isResumed) {
            updationToResumeConductor(destinationMap).doJob()
        }

        if(inNewView) {
            GlobalFrFacade.mapViewController.newMapView(destinationMap, GlobalFrFacade.modeController)
        }
        else {
            GlobalFrFacade.mapViewController.tryToChangeToMapView(destinationMap.url)
        }

        GlobalFrFacade.invokeLater {
            GlobalFrFacade.mapController.select(destination)
            GlobalFrFacade.mapController.centerNode(destination)
        }
    }

//    fun navigateUp() {
//    }
//    fun navigateBack() {
//    }
}