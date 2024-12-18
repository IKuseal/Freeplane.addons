package org.freeplane.features.gtd.glob

import org.freeplane.features.custom.map.MapLifecycleState
import org.freeplane.features.custom.map.clearChildren
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.glob.actions.LoadGtdGlobMapAction
import org.freeplane.features.gtd.glob.actions.ReattachGlobModuleAction
import org.freeplane.features.gtd.glob.attachmodule.ModuleAttacher
import org.freeplane.features.gtd.glob.exceptions.OpenGtdGlobMapExceptionsHandler
import org.freeplane.features.gtd.glob.globspace.GlobSpace
import org.freeplane.features.gtd.glob.scheme.GlobMap
import org.freeplane.features.gtd.glob.scheme.GlobSchemeAccessor
import org.freeplane.features.gtd.glob.scheme.ModulePlaceholder
import org.freeplane.features.map.MapController
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.NodeModel.Side
import org.freeplane.features.map.mindmapmode.MMapModel
import org.freeplane.features.url.mindmapmode.MapLifecycleConductor

object GtdGlobController {
    const val SCHEME_MAP_ID = "GLOB-SCHEME.gtd"
    const val GLOB_MAP_ID = "PlaceholderMap"

    fun init() {
        GtdController.run {
            addAction(LoadGtdGlobMapAction())
            addAction(ReattachGlobModuleAction())
        }
    }

    // load glob map *****************************************************************************
    fun loadGlobMap() {
        val globMap = GlobMap(createGlobMap())

        val modulePlaceholdersById = globMap.modulePlaceholders.associateBy { it.id }
        val modulePlaceholderIds = modulePlaceholdersById.keys

        val modulesDataById = GlobSpace.findModules().associateBy { it.id }
        val modulesDataIds = modulesDataById.keys

        modulePlaceholderIds.intersect(modulesDataIds).forEach {
            ModuleAttacher.attach(modulesDataById[it]!!, modulePlaceholdersById[it]!!)
        }

        OpenGtdGlobMapExceptionsHandler.handle(globMap.map, modulesDataById, modulePlaceholdersById)

        publishGlobMap(globMap.map)
    }

    private fun createGlobMap() : MapModel {
        fun initNewMap() : MapModel {
            val map = GlobSchemeAccessor(SCHEME_MAP_ID).newMap().apply {
                url = SpaceController.getUrlOfMap(GLOB_MAP_ID)!!
            }

            MapLifecycleConductor().run {
                update(map as MMapModel)
                targetState = MapLifecycleState.STARTED
                doJob()
            }

            return map
        }

        fun reuseOpened() : MapModel {
            class ChildData(
                val originNode : NodeModel
            ) {
                val side : Side = originNode.side
                val index : Int = originNode.parentNode.getIndex(originNode)
                lateinit var node : NodeModel
            }

            val url = SpaceController.getUrlOfMap(GLOB_MAP_ID)!!
            val map = GtdController.mapController.getMap(url)
            val rootNode = map.rootNode
            GtdController.mapController.clearChildren(rootNode)

            // a bit not legal
            val schemeMap = initNewMap()

            val childrenData = arrayListOf<ChildData>().apply {
                schemeMap.rootNode.children.forEach {
                    add(ChildData(it))
                }
            }

            childrenData.forEach {
                val node = GtdController.pasteXmlWithoutUndo(rootNode, GtdController.copyAsXml(it.originNode))
                it.node = node
                node.side = it.side
            }

            GtdController.mapController.clearChildren(rootNode)

            childrenData.forEach {
                GtdController.mapController.insertNode(it.node, rootNode, it.index)
            }

            return map
        }

        return if(GtdController.mapController.getMap(SpaceController.getUrlOfMap(GLOB_MAP_ID)!!) != null) {
            reuseOpened()
        }
        else initNewMap()
    }

    private fun publishGlobMap(map : MapModel) {
        MapLifecycleConductor().run {
            update(map as MMapModel)
            targetState = MapLifecycleState.RESUMED
            doJob()
        }
    }

    fun reattachModule(rootNode : NodeModel) {
        val modulePlaceholder = ModulePlaceholder(rootNode)
        val moduleData = GlobSpace.findModule(modulePlaceholder)!!
        ModuleAttacher.reattach(moduleData, modulePlaceholder)
    }
}