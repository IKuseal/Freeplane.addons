package org.freeplane.features.custom.synchronizednodes

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.MapLifecycleState
import org.freeplane.features.custom.map.stream
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.custom.space.isResumed
import org.freeplane.features.custom.treestream.fold
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.NodeModel
import org.freeplane.features.url.mindmapmode.MapLifecycleConductor

object SyncNodeController {
    fun init() {
        GlobalFrFacade.run {
            addAction(SyncNodePullUpdatesAction())
            addAction(SyncNodePushUpdatesAction())
            addAction(NavigateToSyncNodeOriginAction())
        }
    }

    // sync handlers ************************************************************************************************
    private val syncHandlers : MutableList<SyncNodeSyncHandler> = arrayListOf()

    fun addSyncHandler(it : SyncNodeSyncHandler) {
        syncHandlers.add(it)
    }

    private fun firePush(origin: NodeModel, synced: NodeModel) {
        syncHandlers.forEach { it.onSync(origin, synced) }
        syncHandlers.forEach { it.onPush(origin, synced) }
    }

    private fun firePull(origin: NodeModel, synced: NodeModel) {
        syncHandlers.forEach { it.onSync(origin, synced) }
        syncHandlers.forEach { it.onPull(origin, synced) }
    }

    // sync ****************************************************************************************************
    // push *******************************************
    fun pushUpdates(syncNodes: Collection<NodeModel>) {
        val currentMapView = GlobalFrFacade.currentMapView

        val syncTasks = syncNodes.map(::SyncTask)
        val syncTasksByOriginMapId = syncTasks.groupBy { it.originMapId }
        syncTasksByOriginMapId.forEach { (originMapId, syncTasks) ->
            pushUpdates(originMapId, syncTasks)
        }

        GlobalFrFacade.mapViewController.changeToMapView(currentMapView)
    }

    private fun pushUpdates(originMapId : String, syncTasks : Collection<SyncTask>) {
        val originMap = loadOriginMap(originMapId)
        findOriginNodes(originMap, syncTasks)
        syncTasks.forEach { pushUpdates(it) }

        originMap.isSaved = false

        originMap.takeIf { !it.isResumed }
            ?.let { GlobalFrFacade.save(it) }
    }

    private fun pushUpdates(syncTask : SyncTask) {
        firePush(syncTask.originNode, syncTask.syncNode)
    }

    // pull *******************************************
    fun pullUpdates(syncNodes: Collection<NodeModel>) {
        TODO("Not yet implemented")
    }

    // general of sync *********************************
    private fun loadOriginMap(mapId : String) : MapModel =
        MapLifecycleConductor().run {
            load(SpaceController.getMapFile(mapId)!!)
            targetState = MapLifecycleState.STARTED
            isLookupCash = true
            doJob()
        }


    private fun findOriginNodes(originMap : MapModel, syncTasks: Collection<SyncTask>) {
        syncTasks.forEach { it.originNode = findOriginNode(originMap, it.originId) }
    }

    private fun findOriginNode(originMap: MapModel, originId : String) =
        originMap.getNodeForID(originId)!!

    // **************************************************************************************************************
    // **************************************************************************************************************
    // **************************************************************************************************************
    // **************************************************************************************************************
    fun test() {
        val balvanka = testOpenMap("Balvanka")
        val balvanka3 = testOpenMap("Balvanka3")

        val originsById = testSyncNodes(balvanka)
        val syncNodesById = testSyncNodes(balvanka3)

        val syncPairs = originsById.map { (originId, originNode) -> originNode to syncNodesById[originId]!! }

        syncPairs.forEach { (origin, sync) ->
            sync.syncNodeExtension = SyncNodeExtension(origin)
        }
    }

    private fun testOpenMap(name: String) =
        MapLifecycleConductor().run {
            load(SpaceController.getMapFile(name))
            targetState = MapLifecycleState.RESUMED
            useCash()
            doJob()
        }

    private fun testSyncNodes(map : MapModel) : Map<String, NodeModel> {
        val nodes = map.rootNode.stream()
            .fold(arrayListOf<NodeModel>()) { list, it ->
                if (it.text.startsWith("s")) list.add(it)
                list
            }
        val nodesById = nodes.associateBy { it.text }

        return nodesById
    }
}