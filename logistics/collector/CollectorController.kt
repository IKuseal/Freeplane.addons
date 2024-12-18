package org.freeplane.features.logistics.collector

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.ONLY_IMAGE_NODE_STYLE
import org.freeplane.features.custom.image.CustomImagesStore
import org.freeplane.features.custom.map.applyStyle
import org.freeplane.features.custom.map.name
import org.freeplane.features.custom.space.MapResourcesAccessor
import org.freeplane.features.custom.space.SpaceArea
import org.freeplane.features.gtd.data.elements.GtdNum
import org.freeplane.features.gtd.data.elements.GtdNum.ND
import org.freeplane.features.gtd.data.elements.GtdStateClass
import org.freeplane.features.gtd.data.elements.GtdStateClass.I
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.layers.GcsType.GLOB
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.gtd.tag.GTD_PLANE_DEFAULT
import org.freeplane.features.gtd.tag.GTD_TAG_COLLECTOR
import org.freeplane.features.logistics.LogisticsController
import org.freeplane.features.logistics.delivery.DeliveryController
import org.freeplane.features.logistics.search.DestinationSearchModel
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.NodeModel
import java.io.File

object CollectorController {
    fun init() {
        GlobalFrFacade.run {
            addAction(InstantCollectAction())
        }
    }

    var linkToCollect : String = ""
    val isLinkToCollectSet get() = linkToCollect.isNotEmpty()

    val setLinkToCollectAction = SetLinkToCollectAction()
    val resetLinkToCollectorAction = ResetLinkToCollectAction()

    val currentMapCollectorAction : CollectorAction
    val specificMapCollectorAction : CollectorAction
    val workspaceCollectorAction : CollectorAction
    val cashedCollectorAction : CollectorAction
    val scopeCollectorAction : CollectorAction
    val addressCollectorAction : CollectorAction
    val instantCollectAction = InstantCollectAction()
    val instantCollectToSelectedNodeAction = InstantCollectToSelectedNodeAction()

    init {
        val cm = ::DestinationSearchModel
        currentMapCollectorAction = CollectorAction("CURRENT_MAP") { cm().apply { searchArea = SpaceArea.CURRENT_MAP } }
        specificMapCollectorAction = CollectorAction("SPECIFIC_MAP") {
            cm().apply {
                searchArea = SpaceArea.SPECIFIC_MAP
            }
        }
        workspaceCollectorAction = CollectorAction("WORKSPACE") { cm().apply { searchArea = SpaceArea.WORKSPACE } }
        cashedCollectorAction = CollectorAction("CASHED") { cm().apply { searchArea = SpaceArea.CASHED } }
        scopeCollectorAction = CollectorAction("SCOPE") {
            cm().apply {
                searchArea = SpaceArea.WORKSPACE
                fixedScope = LogisticsController::fixedScope::get
            }
        }
        addressCollectorAction = CollectorAction("Address") {
            cm().apply {
                searchArea = SpaceArea.WORKSPACE
                fixedAddress = LogisticsController::fixedAddress::get
            }
        }
    }

    fun createDataNode(map : MapModel): NodeModel {
        val dtca = DataToCollectAccessor

        val node = NodeModel(dtca.getDataAsString(), map)

        when {
            dtca.isLinkData -> GlobalFrFacade.setLink(node, dtca.getLink())
            dtca.isImageData -> {
                val imageFile = dtca.writeImageToFile()
                importImage(node, imageFile)
                node.applyStyle(ONLY_IMAGE_NODE_STYLE)
            }
        }

//        val intent = GtdIntent(I(ND), listOf(GTD_PLANE_DEFAULT, GTD_TAG_COLLECTOR), GLOB)
//        node.gtdNode.addIntent(intent)

        return node
    }

    private fun importImage(node: NodeModel, imageFile: File) {
        val imageFile = DataToCollectAccessor.getImageFile().let {
            MapResourcesAccessor(node.map.name).run {
                putImage(it, "${CustomImagesStore.generateName()}.png")
            }
        }

        GlobalFrFacade.viewerController.pasteImage(imageFile.toURI(), node)
    }

    fun instantCollect(destinationNode : NodeModel, toUseMail : Boolean = true) {

        val collectNode = createDataNode(destinationNode.map)
        if(isLinkToCollectSet)
            GlobalFrFacade.setLink(collectNode, linkToCollect)

        DeliveryController.deliver(collectNode, destinationNode, toUseMail)
    }
}