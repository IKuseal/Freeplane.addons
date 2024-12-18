package org.freeplane.features.gtd.core

import org.freeplane.features.custom.IFrEventsAnnouncer
import org.freeplane.features.gtd.actions.GtdIsActiveEnabler
import org.freeplane.features.gtd.actions.GtdIsGlobMapSelectedEnabler
import org.freeplane.features.gtd.actions.GtdIsInstalledEnabler
import org.freeplane.features.gtd.actions.GtdIsModuleExistEnabler
import org.freeplane.features.gtd.allmaps.ImportListOfGtdIsAttachedToGlobMaps
import org.freeplane.features.gtd.allmaps.ImportListOfGtdIsInstalledMaps
import org.freeplane.features.gtd.clipboard.GtdClipboardController
import org.freeplane.features.gtd.configuration.GtdConfigurationController
import org.freeplane.features.gtd.filter.GtdFilterController
import org.freeplane.features.gtd.glob.GtdGlobController
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.intent.actions.GtdSelectedIntentExistsEnabler
import org.freeplane.features.gtd.intentsfiltering.GtdIntentsFilteringController
import org.freeplane.features.gtd.layers.GtdLayerController
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.miscellaneous.GtdMiscellaneousController
import org.freeplane.features.gtd.module.GtdModuleController
import org.freeplane.features.gtd.readwrite.GtdMigrationController
import org.freeplane.features.gtd.tag.GtdTagController
import org.freeplane.features.gtd.transparency.GtdTransparencyController
import org.freeplane.features.gtd.views.GtdViewsController
import org.freeplane.features.gtd.views.gcsview.GcsViewController
import org.freeplane.features.map.IMapLifeCycleListener
import org.freeplane.features.url.mindmapmode.MapLifecycleConductor

object GtdController : IFrEventsAnnouncer by FrEventsAnnouncer, IFrFacade by FrFacade, IMapLifeCycleListener {

    val a : MapLifecycleConductor? by lazy { MapLifecycleConductor() }

    fun init() {
        GtdConfigurationController.init()
        GtdMapController.init()
        GtdMapCoverController.init()
        GtdTagController.init()
        GtdIntentController.init()
        GtdCoverIntentController.init()
        GtdModuleController.init()
        GtdClipboardController.init()
        GtdTransparencyController.init()
        GtdFilterController.init()
        GtdGlobController.init()
        GtdLayerController.init()
        GtdViewsController.init()
        GtdMiscellaneousController.init()
        GtdMigrationController.init()
        GtdIntentsFilteringController.init()

        // FrEventsAnnouncer
        mapController.addUINodeChangeListener(FrEventsAnnouncer)
        mapController.addNodeSelectionListener(FrEventsAnnouncer)
        mapController.addUIMapChangeListener(FrEventsAnnouncer)
        resourceController.addPropertyChangeListener(FrEventsAnnouncer)
        mapViewController.addMapSelectionListener(FrEventsAnnouncer)
        mapController.addMapLifeCycleListener(FrEventsAnnouncer)
        mapViewController.addMapViewChangeListener(FrEventsAnnouncer)

        mapViewController.addMapViewChangeListener(GtdMapCoverController)

        GtdMapCoverController.run {
            addGtdMapCoverChangeListener(GtdTransparencyController)
            addGtdMapCoverChangeListener(GtdLayerController)
        }

        initActions()

        configureSomeDependencies()

//        performGtdConfigurations()
    }

    private fun configureSomeDependencies() {
        GtdFilterController.addFilteringListener(GcsViewController)
    }

    private fun initActions() {
        registerEnabler(GtdIsActiveEnabler)
        registerEnabler(GtdIsInstalledEnabler)
        registerEnabler(GtdIsModuleExistEnabler)
        registerEnabler(GtdIsGlobMapSelectedEnabler)
        registerEnabler(GtdSelectedIntentExistsEnabler)

//        addAction(TestFunctionalityAction())
        addAction(ImportListOfGtdIsInstalledMaps())
        addAction(ImportListOfGtdIsAttachedToGlobMaps())
    }

//    private fun performGtdConfigurations() {
//    }
}