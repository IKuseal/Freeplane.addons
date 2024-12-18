package org.freeplane.features.gtd.core

import org.freeplane.core.io.ReadManager
import org.freeplane.core.io.WriteManager
import org.freeplane.core.resources.ResourceController
import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.core.ui.menubuilders.generic.PhaseProcessor
import org.freeplane.core.util.Hyperlink
import org.freeplane.features.custom.map.readwrite.MapLoaderFacade
import org.freeplane.features.customjava.AActionsEnabler
import org.freeplane.features.filter.FilterController
import org.freeplane.features.icon.IconStore
import org.freeplane.features.icon.NamedIcon
import org.freeplane.features.icon.UIIcon
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.MapReader
import org.freeplane.features.map.MapWriter
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.mindmapmode.MMapController
import org.freeplane.features.mode.Controller
import org.freeplane.features.mode.mindmapmode.MModeController
import org.freeplane.features.ui.ViewController
import org.freeplane.features.url.mindmapmode.MFileManager
import org.freeplane.view.swing.map.MapView
import org.freeplane.view.swing.map.MapViewController
import java.awt.Frame
import java.net.URI
import java.util.stream.Stream

interface IFrFacade {
    // events **********************************************************************************
    fun nodeChanged(node: NodeModel, property: Any?, oldValue: Any? = null, newValue: Any? = null)

    fun nodeRefresh(node: NodeModel, property: Any?, oldValue: Any? = null, newValue: Any? = null)

    fun fireMapChanged(
        source: Any?, map: MapModel, property: Any?, oldValue: Any? = null,
        newValue: Any? = null, setsDirtyFlag: Boolean = true
    )

    // actions ***************************************************************************************************
    fun addActionIfNotAlreadySet(action: AFreeplaneAction): AFreeplaneAction

    fun addAction(action: AFreeplaneAction)

    fun removeAction(key: String): AFreeplaneAction

    fun removeActionIfSet(key: String): AFreeplaneAction?

    // enablers ***********************************************************************************
    fun unregisterEnabler(enabler: AActionsEnabler)

    fun registerEnabler(enabler: AActionsEnabler): AActionsEnabler?

    // menu ***************************************************************************************************
    fun addUiBuilder(phase: PhaseProcessor.Phase, name: String, builder: EntryVisitor, destroyer: EntryVisitor)

    // свита *****************************************************************************************************
    val modeController : MModeController

    val controller : Controller

    val mapController : MMapController

    val mapViewController : MapViewController

    val resourceController : ResourceController

    val filterController : FilterController

    val currentMap : MapModel

    val selectedNode: NodeModel

    val viewController: ViewController

    // heap ******************************************************************************************
    fun invokeLater(runnable: Runnable)

    fun streamOf(nodeModel: NodeModel): Stream<NodeModel>

    fun addUiBuilder(key: String, builder: EntryVisitor)
    val currentMapViewSafe: MapView?
    val frame: Frame
    val currentMapSafe: MapModel?
    val selectedNodeSafe: NodeModel?
    val currentMapView: MapView
    val readManager: ReadManager
    val writeManager: WriteManager
    val mapLoader: MapLoaderFacade
    fun getLink(node: NodeModel): Hyperlink?
    fun getLinkToNode(node: NodeModel): String
    fun getIdUsingLinkToNode(node: NodeModel): String
    val mapReader: MapReader
    val mapWriter: MapWriter
    fun copyAsXml(node: NodeModel): String
    fun pasteXmlWithoutUndo(target: NodeModel, pasted: String): NodeModel
    fun setLink(node: NodeModel, link: String)
    val selectedNodes: List<NodeModel>
    val currentMapFolderPath: URI
    val fileManager: MFileManager
    fun save(map: MapModel)
    fun getIdUsingLinkToNode(link: String): String
    val iconStore: IconStore
    fun getIcon(name: String): UIIcon?
    fun getIconsStandard(node: NodeModel): MutableCollection<NamedIcon>?
    fun pasteXml(target: NodeModel, pasted: String): NodeModel
}