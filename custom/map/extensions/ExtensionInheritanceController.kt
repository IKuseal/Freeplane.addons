package org.freeplane.features.custom.map.extensions

import org.freeplane.core.extension.IExtension
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.*
import kotlin.reflect.KClass
import org.freeplane.features.custom.map.extensions.TreeSpread.*
import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.data.elements.GtdStateClass
import org.freeplane.features.gtd.data.elements.GtdSubStateClass
import org.freeplane.features.gtd.layers.GtdLayer
import org.freeplane.features.gtd.map.GtdMapController.gtdNodeEventSpreadProp
import org.freeplane.features.map.*
import org.freeplane.features.ui.IMapViewChangeListener
import org.freeplane.view.swing.map.MapView
import java.awt.Component

object ExtensionInheritanceController : INodeChangeListener, IMapChangeListener,
    IMapSelectionListener, IMapLifeCycleListener, IMapViewChangeListener {

    val evenProp : String = ExtensionInheritanceController::class.java.name

    val inheritableExtensions = arrayListOf<KClass<out IExtension>>().apply {
        add(GtdStateClass.I.spreadClass)
        GtdSubStateClass.values().forEach { add(it.spreadClass) }
        add(GtdLayer::class)
    }

    override fun mapChanged(event: MapChangeEvent?) {}

    override fun onNodeDeleted(nodeDeletionEvent: NodeDeletionEvent?) {
        nodeDeletionEvent?.node?.rootNode?.let { delayedRefreshInheritance(it) }
    }

    override fun onNodeInserted(parent: NodeModel?, child: NodeModel?, newIndex: Int) {
        parent?.rootNode?.let { delayedRefreshInheritance(it) }
    }

    override fun onNodeMoved(nodeMoveEvent: NodeMoveEvent?) {
        nodeMoveEvent?.newParent?.rootNode?.let { delayedRefreshInheritance(it) }
    }

    // no need because of afterViewChange?
//    override fun afterMapChange(oldMap: MapModel?, newMap: MapModel?) {
//        super.afterMapChange(oldMap, newMap)
//    }

    override fun nodeChanged(event: NodeChangeEvent?) {
        val prop = event?.property ?: return
        val root = event?.node?.rootNode ?: return

        when(prop) {
            is GtdEventProp -> when {
                prop.iz(gtdNodeEventSpreadProp) -> delayedRefreshInheritance(root)
            }
        }
    }

    override fun afterViewChange(oldView: Component?, newView: Component?) {
        val mapView = newView as? MapView ?: return
        refreshInheritance(mapView.root!!.model)
    }

    override fun onCreate(map: MapModel) {
        refreshInheritance(map.rootNode)
    }

    // refreshing ****************************************************************************************
    object DelayedRefresher {
        private var isRefreshSet = false

        fun refresh(root: NodeModel) {
            if(isRefreshSet) return

            isRefreshSet = true

            GlobalFrFacade.invokeLater {
                refreshInheritance(root)
                isRefreshSet = false
            }
        }
    }

    private class InheritanceRefresher(val clazz : KClass<out IExtension>) {
        fun refreshInheritance(node : NodeModel, inheritedsOfType : InheritedExtensionsOfType<IExtension>) =
            node._refreshInheritance(inheritedsOfType)

        fun NodeModel._refreshInheritance(pInheritedsOfType : InheritedExtensionsOfType<IExtension>) :
                InheritedExtensionsOfType<IExtension> {

            val inheritedsOfType = pInheritedsOfType.copy()

            val nodeCover = currentNodeCover

            // updating inheritance for this node
            nodeCover.inheritedExtensions.removeInheritedOfType(clazz)

            if(containsNotToInheritExtension(clazz)) inheritedsOfType.clear()

            if(!inheritedsOfType.isEmpty)
                nodeCover.inheritedExtensions
                    .createInheritedOfType(clazz)
                    .addAll(inheritedsOfType.list)

            return getInheritedsForChildren(clazz, inheritedsOfType)
        }
    }

    fun delayedRefreshInheritance(root : NodeModel) {
        DelayedRefresher.refresh(root)
    }

    fun refreshInheritance(root : NodeModel) {
        inheritableExtensions.forEach { refreshInheritance(root, it) }

        GlobalFrFacade.fireMapChanged(ExtensionInheritanceController::class.java, root.map, evenProp)
    }

    private fun refreshInheritance(root: NodeModel, clazz: KClass<out IExtension>) {
        val refresher = InheritanceRefresher(clazz)

        val ownInheriteds = root.parentNode?.getInheritedsForChildren(clazz) ?: InheritedExtensionsOfType()

        root.forEachWithSpread(ownInheriteds, action = refresher::refreshInheritance)
    }

    private fun NodeModel.getInheritedsForChildren(clazz : KClass<out IExtension>) :
            InheritedExtensionsOfType<IExtension> {
        val ownInheriteds = currentNodeCover.inheritedExtensions[clazz]
        val inheritedsForChildren = InheritedExtensionsOfType<IExtension>()

        ownInheriteds?.let { inheritedsForChildren.addAll(it.list) }

        return getInheritedsForChildren(clazz, inheritedsForChildren)
    }


    private fun NodeModel.getInheritedsForChildren(clazz : KClass<out IExtension>,
                                                   ownInheriteds : InheritedExtensionsOfType<IExtension>
    ) :
            InheritedExtensionsOfType<IExtension> {

        ownInheriteds.removeChildInherited()

        getExtNoCast(clazz, DESCENDANT)?.let { ownInheriteds.add(it) }
        getExtNoCast(clazz, CHILD     )?.let { ownInheriteds.add(it) }

        return ownInheriteds
    }
}