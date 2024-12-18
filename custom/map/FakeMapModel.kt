package org.freeplane.features.custom.map

import org.freeplane.core.undo.IActor
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.readwrite.MapLoaderFacade
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.NodeModel
import org.freeplane.features.url.mindmapmode.MapLifecycleConductor

object FakeMapModel {
    private const val fakeMapId = "FakeMap"
    private val fakeMapFile get() = SpaceController.getMap(fakeMapId)!!

//    val map : MapModel = MapLoaderFacade.createMap()

    val map : MapModel = MapLifecycleConductor().apply {
        load(fakeMapFile.file)
        targetState = MapLifecycleState.STARTED
    }.doJob()

    private val root = map.rootNode!!

    val usualFakeNode = object : NodeModel("USUAL_FAKE_NODE", map){}.also {
        root.insert(it)
    }

    fun newNode(userObject : Any? = null) = createNode(userObject).also {
        insertNode(it)
    }

    fun createNode(userObject : Any? = null) =
        if(userObject != null) NodeModel(userObject, map) else NodeModel(map)

    fun insertNode(node : NodeModel) {
        val actor = object : IActor {
            override fun act() {
                root.insert(node)
            }

            override fun getDescription() = "FakeMapModel : insert node"

            override fun undo() {
                root.removeChild(node)
            }
        }

        execute(actor)
    }

//    fun removeNode(node : NodeModel) {
//        root.removeChild(node)
//    }

    private fun execute(actor : IActor) {
        GlobalFrFacade.run {
            modeController.execute(actor, currentMap)
        }
    }


}

val MapModel.isFakeMap get() = FakeMapModel.map === this