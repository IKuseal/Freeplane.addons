package org.freeplane.features.gtd.core

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.core.ui.menubuilders.generic.PhaseProcessor
import org.freeplane.core.util.Hyperlink
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.readwrite.MapLoaderFacade
import org.freeplane.features.customjava.AActionsEnabler
import org.freeplane.features.link.NodeLinks
import org.freeplane.features.map.MapChangeEvent
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.NodeStream
import org.freeplane.features.ui.ViewController
import org.freeplane.view.swing.map.MapView
import java.awt.Frame
import java.net.URI
import java.util.stream.Stream

object FrFacade : IFrFacade {

    // events **********************************************************************************
    override fun nodeChanged(node: NodeModel, property: Any?, oldValue: Any?, newValue: Any?) {
        mapController.nodeChanged(node, property, oldValue, newValue)
    }

    override fun nodeRefresh(node: NodeModel, property: Any?, oldValue: Any?, newValue: Any?) {
        mapController.nodeRefresh(node, property, oldValue, newValue)
    }

    override fun fireMapChanged(
        source: Any?, map: MapModel, property: Any?, oldValue: Any?,
        newValue: Any?, setsDirtyFlag: Boolean
    ) {
        mapController.fireMapChanged(MapChangeEvent(source, map, property, oldValue, newValue, setsDirtyFlag))
    }

    // actions ***************************************************************************************************
    override fun addActionIfNotAlreadySet(action: AFreeplaneAction): AFreeplaneAction {
        return GlobalFrFacade.addActionIfNotAlreadySet(action)
    }

    override fun addAction(action: AFreeplaneAction) {
        GlobalFrFacade.addAction(action)
    }

    override fun removeAction(key: String): AFreeplaneAction {
        return GlobalFrFacade.removeAction(key)
    }

    override fun removeActionIfSet(key: String): AFreeplaneAction? {
        return GlobalFrFacade.removeActionIfSet(key)
    }

    // enablers ***************************************************************************************************
    override fun registerEnabler(enabler: AActionsEnabler): AActionsEnabler? {
        return GlobalFrFacade.registerEnabler(enabler)
    }

    override fun unregisterEnabler(enabler: AActionsEnabler) {
        GlobalFrFacade.unregisterEnabler(enabler)
    }

    // menu ***************************************************************************************************
    override fun addUiBuilder(phase: PhaseProcessor.Phase, name: String, builder: EntryVisitor, destroyer: EntryVisitor) {
        modeController.addUiBuilder(phase, name, builder, destroyer)
    }

    // threads ***************************************************************************************************
    override fun invokeLater(runnable : Runnable) = GlobalFrFacade.invokeLater(runnable)

    // links ***********************************************************************************************
    override fun getLink(node : NodeModel) : Hyperlink? = NodeLinks.getLink(node)

    override fun getLinkToNode(node : NodeModel) : String = "${node.map.url.toURI()}#${node.id}"

    override fun getIdUsingLinkToNode(node : NodeModel) = getIdUsingLinkToNode(getLinkToNode(node))

    override fun getIdUsingLinkToNode(link : String) = link.run { split("/").last() }

    override fun setLink(node : NodeModel, link : String) = GlobalFrFacade.setLink(node, link)

    // copy/paste ************************************************************************************************
    override fun copyAsXml(node : NodeModel) = GlobalFrFacade.copyAsXml(node)

    override fun pasteXml(target : NodeModel, pasted : String) =
        GlobalFrFacade.pasteXml(target, pasted)

    override fun pasteXmlWithoutUndo(target : NodeModel, pasted : String) =
        GlobalFrFacade.pasteXmlWithoutUndo(target, pasted)

    // свита *****************************************************************************************************
    override val controller by GlobalFrFacade::controller

    override val modeController by GlobalFrFacade::modeController

    override val mapController by GlobalFrFacade::mapController

    override val mapViewController by GlobalFrFacade::mapViewController

    override val viewController : ViewController get() = controller.viewController

    override val resourceController by GlobalFrFacade::resourceController

    override val filterController by GlobalFrFacade::filterController

    override val fileManager by GlobalFrFacade::fileManager

    override val readManager get() = mapController.readManager!!

    override val writeManager get() = mapController.writeManager!!

    override val mapReader by GlobalFrFacade::mapReader

    override val mapWriter by GlobalFrFacade::mapWriter

    override val mapLoader : MapLoaderFacade by GlobalFrFacade::mapLoader

    override val iconStore by GlobalFrFacade::iconStore

    override val currentMap: MapModel by GlobalFrFacade::currentMap

    override val currentMapSafe: MapModel? by GlobalFrFacade::currentMapSafe

    override val currentMapView : MapView by GlobalFrFacade::currentMapView

    override val currentMapViewSafe : MapView? by GlobalFrFacade::currentMapViewSafe

    override val selectedNode : NodeModel by GlobalFrFacade::selectedNode

    override val selectedNodeSafe : NodeModel? by GlobalFrFacade::selectedNodeSafe

    override val selectedNodes : List<NodeModel> by GlobalFrFacade::selectedNodes

    override val frame : Frame by GlobalFrFacade::frame

    // icons **********************************************************************************
    override fun getIcon(name : String) = GlobalFrFacade.getIcon(name)

    // heap ********************************************************************************************************
    override fun streamOf(nodeModel: NodeModel): Stream<NodeModel> {
        return NodeStream.of(nodeModel)
    }

    override fun addUiBuilder(key : String, builder : EntryVisitor) {
        GlobalFrFacade.addUiBuilder(key, builder)
    }

    override val currentMapFolderPath : URI get() = currentMap.file.parentFile.toURI()

    override fun save(map : MapModel) {
        GlobalFrFacade.save(map)
    }

    override fun getIconsStandard(node : NodeModel) = GlobalFrFacade.getIconsStandard(node)
}