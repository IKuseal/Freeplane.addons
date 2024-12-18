package org.freeplane.features.custom

import com.tulskiy.keymaster.common.Provider
import org.freeplane.core.resources.ResourceController
import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.components.UITools
import org.freeplane.core.ui.menubuilders.generic.ChildActionEntryRemover
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.core.ui.menubuilders.generic.PhaseProcessor
import org.freeplane.core.util.Hyperlink
import org.freeplane.features.ankikt.glob.AiGlobController
import org.freeplane.features.custom.actions.EnablersManager
import org.freeplane.features.custom.filter.selectiveexclusion.FilterSelectiveExclusionController
import org.freeplane.features.custom.historynavigation.edithistorynavigation.EditHistoryNavigationController
import org.freeplane.features.custom.map.NodeCover
import org.freeplane.features.custom.map.currentNodeCover
import org.freeplane.features.custom.map.extensions.ExtensionInheritanceController
import org.freeplane.features.custom.map.readwrite.MapLoaderFacade
import org.freeplane.features.custom.space.NavigateToMapInSpaceAction
import org.freeplane.features.customjava.AActionsEnabler
import org.freeplane.features.filter.FilterController
import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.filter.condition.ConditionNotSatisfiedDecorator
import org.freeplane.features.gtd.core.FrFacade
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.icon.IconController
import org.freeplane.features.icon.IconStore
import org.freeplane.features.icon.UIIcon
import org.freeplane.features.icon.factory.IconStoreFactory
import org.freeplane.features.icon.mindmapmode.MIconController
import org.freeplane.features.link.NodeLinks
import org.freeplane.features.logistics.collector.CollectorController
import org.freeplane.features.map.MapChangeEvent
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.MapWriter
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.mindmapmode.MMapController
import org.freeplane.features.mode.Controller
import org.freeplane.features.mode.mindmapmode.MModeController
import org.freeplane.features.space.MSpaceController
import org.freeplane.features.styles.LogicalStyleController
import org.freeplane.features.styles.mindmapmode.MLogicalStyleController
import org.freeplane.features.ui.ViewController
import org.freeplane.features.url.mindmapmode.MFileManager
import org.freeplane.view.swing.features.filepreview.ViewerController
import org.freeplane.view.swing.map.MapView
import org.freeplane.view.swing.map.MapViewController
import java.awt.Component
import java.awt.Frame
import java.io.File
import java.io.StringReader
import java.io.StringWriter
import java.net.URI
import java.net.URL
import javax.swing.KeyStroke

object GlobalFrFacade {
    private val provider = Provider.getCurrentProvider(true)

    init {
        addAction(TestFunctionalityAction())
        addAction(NavigateToMapInSpaceAction())
        addAction(UpdateMapStyleOfMapsInSpaceAction())
        addAction(PointerNodeCopyAction())

        FilterSelectiveExclusionController.init()
        AiGlobController.init()
        EditHistoryNavigationController.init()

        registerGlobalHotKeys()
    }

    // свита *****************************************************************************************************
    val controller get() = Controller.getCurrentController() as Controller

    val modeController get() = Controller.getCurrentModeController() as MModeController

    val mapController get() = modeController.mapController as MMapController

    val mapViewController get() = controller.mapViewManager as MapViewController

    val viewController : ViewController get() = controller.viewController

    val viewerController : ViewerController get() = modeController.getExtension(ViewerController::class.java)

    val resourceController get() = ResourceController.getResourceController() as ResourceController

    val filterController get() = FilterController.getCurrentFilterController() as FilterController

    val currentMap: MapModel get() = controller.map

    val currentMapSafe: MapModel? get() = controller.map

    val currentMapView : MapView get() = mapViewController.mapView

    val currentMapViewSafe : MapView? get() = mapViewController.mapView

    val selectedNode : NodeModel get() = mapController.selectedNode

    val selectedNodeSafe : NodeModel?
        get() = mapController.selectedNode

    val selectedNodes : List<NodeModel> get() = ArrayList<NodeModel>(mapController.selectedNodes)

    val selectedNodeC : NodeCover get() = selectedNode.currentNodeCover

    val frame : Frame get() = UITools.getCurrentFrame()

    val mapLoader : MapLoaderFacade get() = MapLoaderFacade

    val fileManager : MFileManager get() = MFileManager.getController(modeController)

    val mapReader get() = FrFacade.mapController.mapReader!!

    val mapWriter get() = FrFacade.mapController.mapWriter!!

    val iconStore : IconStore get() = IconStoreFactory.ICON_STORE

    val iconController : MIconController get() = IconController.getController() as MIconController

    val logicalStyleController : MLogicalStyleController get() = LogicalStyleController.getController() as MLogicalStyleController

    // enablers **************************************************************************************************
    fun registerEnabler(enabler: AActionsEnabler): AActionsEnabler? {
        return EnablersManager.registerEnabler(enabler)
    }

    fun unregisterEnabler(enabler: AActionsEnabler) {
        EnablersManager.unregisterEnabler(enabler)
    }

    // actions **************************************************************************************************
    fun addActionIfNotAlreadySet(action: AFreeplaneAction): AFreeplaneAction {
        val existingAction: AFreeplaneAction = modeController.addActionIfNotAlreadySet(action)
        return if(existingAction !== action) existingAction
        else {
            if(!action.enabledProps.isEmpty()) EnablersManager.registerAction(action)
            action
        }
    }

    fun addAction(action: AFreeplaneAction) {
        modeController.addAction(action)
        if (!action.enabledProps.isEmpty()) EnablersManager.registerAction(action)
    }

    fun removeAction(key: String): AFreeplaneAction {
        val removedAction: AFreeplaneAction = modeController.removeAction(key)
        if (!removedAction.enabledProps.isEmpty()) EnablersManager.unregisterAction(removedAction)
        return removedAction
    }

    fun removeActionIfSet(key: String): AFreeplaneAction? {
        val removedAction: AFreeplaneAction? = modeController.removeActionIfSet(key)
        if (removedAction != null && !removedAction.enabledProps.isEmpty())
            EnablersManager.unregisterAction(removedAction)
        return removedAction
    }

    // events **********************************************************************************
    fun nodeChanged(node: NodeModel, property: Any?, oldValue: Any? = null, newValue: Any? = null) {
        mapController.nodeChanged(node, property, oldValue, newValue)
    }

    fun nodeRefresh(node: NodeModel, property: Any?, oldValue: Any?, newValue: Any?) {
        mapController.nodeRefresh(node, property, oldValue, newValue)
    }

    fun fireMapChanged(
        source: Any?, map: MapModel, property: Any?, oldValue: Any? = null,
        newValue: Any? = null, setsDirtyFlag: Boolean = true
    ) {
        mapController.fireMapChanged(MapChangeEvent(source, map, property, oldValue, newValue, setsDirtyFlag))
    }

    // icons ***************************************************************************************
    fun getIcon(name : String) : UIIcon? = iconStore.getUIIcon(name)

    // global hot keys *******************************************************************************
    fun registerGlobalHotKeys() {
        provider.register(KeyStroke.getKeyStroke("control alt J")) {
            CollectorController.currentMapCollectorAction.actionPerformed(null)
        }
        provider.register(KeyStroke.getKeyStroke("control shift J")) {
            CollectorController.specificMapCollectorAction.actionPerformed(null)
        }
        provider.register(KeyStroke.getKeyStroke("alt shift J")) {
            CollectorController.workspaceCollectorAction.actionPerformed(null)
        }
        provider.register(KeyStroke.getKeyStroke("alt control shift J")) {
            CollectorController.cashedCollectorAction.actionPerformed(null)
        }
        provider.register(KeyStroke.getKeyStroke("control J")) {
            CollectorController.scopeCollectorAction.actionPerformed(null)
        }
        provider.register(KeyStroke.getKeyStroke("alt shift SPACE")) {
            CollectorController.addressCollectorAction.actionPerformed(null)
        }
        provider.register(KeyStroke.getKeyStroke("control shift SPACE")) {
            CollectorController.instantCollectAction.actionPerformed(null)
        }
        provider.register(KeyStroke.getKeyStroke("control SPACE")) {
            CollectorController.instantCollectToSelectedNodeAction.actionPerformed(null)
        }
        provider.register(KeyStroke.getKeyStroke("alt shift K")) {
            CollectorController.setLinkToCollectAction.actionPerformed(null)
        }
        provider.register(KeyStroke.getKeyStroke("alt control shift K")) {
            CollectorController.resetLinkToCollectorAction.actionPerformed(null)
        }
    }

    fun unregisterGlobalHotKeys() {
        provider.reset()
        provider.stop()
    }

    // heap ***********************************************************************************************
    fun invokeLater(runnable : Runnable) {
        viewController.invokeLater(runnable)
    }

    fun addUiBuilder(phase: PhaseProcessor.Phase, name: String, builder: EntryVisitor, destroyer: EntryVisitor) {
        modeController.addUiBuilder(phase, name, builder, destroyer)
    }

    fun addUiBuilder(key : String, builder : EntryVisitor) {
        addUiBuilder(
            PhaseProcessor.Phase.ACTIONS, key,
            builder, ChildActionEntryRemover(GtdController.controller)
        )
    }

    fun addCustomListeners() {
        var l : Any

        l = ExtensionInheritanceController
        mapController.run {
            addUINodeChangeListener(l)
            addMapLifeCycleListener(l)
            addUIMapChangeListener(l)
        }
        mapViewController.run {
            addMapSelectionListener(l)
            addMapViewChangeListener(l)
        }
    }

    fun selectNode(node : NodeModel) {
        mapController.select(node)
    }

    fun getIconsStandard(node : NodeModel) =
        iconController.getIcons(node, LogicalStyleController.StyleOption.FOR_UNSELECTED_NODE)

    fun sameFile(urlToCheck: URL, mapViewUrl: URL?): Boolean {
        if (mapViewUrl == null) {
            return false
        }
        return if (urlToCheck.protocol == "file" && mapViewUrl.protocol == "file") {
            File(urlToCheck.file) == File(mapViewUrl.file)
        } else urlToCheck.sameFile(mapViewUrl)
    }

    fun save(map : MapModel) {
        fileManager.save(map)
    }

    fun copyAsXml(node : NodeModel) : String {
        val sWriter = StringWriter()

        mapWriter.writeNodeAsXml(sWriter, node, MapWriter.Mode.FILE, true, true, false)

        return sWriter.toString()
    }

    fun pasteXml(target : NodeModel, pasted : String) : NodeModel {
        val node = mapReader.createNodeTreeFromXml(target.map, StringReader(pasted), MapWriter.Mode.FILE)
        node.map = target.map
        mapController.insertNode(node, target, target.childCount)

        return node
    }

    fun pasteXmlWithoutUndo(target : NodeModel, pasted : String) : NodeModel {
        val node = mapReader.createNodeTreeFromXml(target.map, StringReader(pasted), MapWriter.Mode.FILE)
        node.map = target.map
        mapController.insertNodeIntoWithoutUndo(node, target, target.childCount)

        return node
    }

    fun setLink(node : NodeModel, link : String) {
        NodeLinks.createLinkExtension(node).setHyperLink(Hyperlink(URI.create(link)))
    }

    fun getDialogParent(): Component {
        val dialogParent: Component
        val viewFrame = UITools.getCurrentFrame()
        dialogParent =
            if (viewFrame != null && viewFrame.isShowing && viewFrame.extendedState != Frame.ICONIFIED) viewFrame else UITools.getCurrentRootComponent()
        return dialogParent
    }

    fun selectNode(node : NodeModel, toCenter : Boolean = false) {
        GlobalFrFacade.invokeLater {
            mapController.select(node)
            if(toCenter) mapController.centerNode(node)
        }
    }
}

val ASelectableCondition.inverse get() = ConditionNotSatisfiedDecorator(this)

val MAP_FILE_FOR_TESTING get() = MSpaceController.getController().maps.first { it.name == "GTD.mm" }