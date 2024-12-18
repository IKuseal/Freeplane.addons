package org.freeplane.features.logistics.delivery

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.space.SpaceArea
import org.freeplane.features.custom.space.isResumed
import org.freeplane.features.gtd.configuration.isGtdActive
import org.freeplane.features.gtd.tag.GTD_PLANE_DELIVERY
import org.freeplane.features.gtd.data.elements.GtdNum
import org.freeplane.features.gtd.data.elements.GtdStateClass
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.logistics.LogisticsController
import org.freeplane.features.logistics.SwitchToNavigateToDestinationAction
import org.freeplane.features.logistics.search.DestinationSearchModel
import org.freeplane.features.logistics.specialnodes.MailType
import org.freeplane.features.logistics.specialnodes.isMailNode
import org.freeplane.features.map.IMapChangeListener
import org.freeplane.features.map.NodeModel

object DeliveryController : IMapChangeListener {
    fun init() {
        GlobalFrFacade.run {
            val cm = ::DestinationSearchModel
            addAction(DeliverAction("CURRENT_MAP") { cm().apply { searchArea = SpaceArea.CURRENT_MAP } })
            addAction(DeliverAction("SPECIFIC_MAP") { cm().apply { searchArea = SpaceArea.SPECIFIC_MAP } })
            addAction(DeliverAction("WORKSPACE") { cm().apply { searchArea = SpaceArea.WORKSPACE } })
            addAction(DeliverAction("CASHED") { cm().apply { searchArea = SpaceArea.CASHED } })
            addAction(DeliverAction("SCOPE") {
                cm().apply {
                    searchArea = SpaceArea.WORKSPACE
                    fixedScope = LogisticsController::fixedScope::get
                }
            })
            addAction(DeliverAction("Address") {
                cm().apply {
                    searchArea = SpaceArea.WORKSPACE
                    fixedAddress = LogisticsController::fixedAddress::get
                }
            })

            addAction(SwitchToNavigateToDestinationAction())
            addAction(ToAddDeliveryIntentAction())
            addUiBuilder(SetTargetMailTypeMenuBuilder.SET_TARGET_MAIL_TYPE_MENU_BUILDER, SetTargetMailTypeMenuBuilder)
        }
    }

    var targetMailType : MailType = MailType.MAIL

    var toNavigateToDestination = false

    var toAddDeliveryIntent = false
    val deliveryIntent = GtdIntent(GtdStateClass.Q1(GtdNum.ND), listOf(GTD_PLANE_DELIVERY))

    // deliver ***********************************************************************************
    fun deliver(nodesToDeliver: Collection<NodeModel>, destination: NodeModel) : Collection<NodeModel> {
        val deliveredNodes = arrayListOf<NodeModel>()
        nodesToDeliver.forEach {
            deliver(it, destination).also(deliveredNodes::add)
        }
        return deliveredNodes
    }

    fun deliver(nodeToDeliver : NodeModel, destination: NodeModel, toUseMail : Boolean = true) : NodeModel {
        val mailNode = if(toUseMail) targetMailType.ofWithCreate(destination) else destination

//        GlobalFrFacade.mapController.run {
//            deleteNode(nodeToDeliver)
//            nodeToDeliver.map = destination.map
//            insertNode(nodeToDeliver, mailNode)
//        }

        val nodeToDeliverXml = GlobalFrFacade.copyAsXml(nodeToDeliver)

        var deliveredNode : NodeModel

        GlobalFrFacade.mapController.run {
//            if(nodeToDeliver.map != null && !nodeToDeliver.map.isFakeMap)
//                deleteNode(nodeToDeliver)

            nodeToDeliver.takeIf { it.map != null && it.parentNode != null }
                ?.let { deleteNode(it) }

            if(destination.map.isResumed)
                deliveredNode = GlobalFrFacade.pasteXml(mailNode, nodeToDeliverXml)
            else deliveredNode = GlobalFrFacade.pasteXmlWithoutUndo(mailNode, nodeToDeliverXml)
        }

        addDeliveryIntentInternal(deliveredNode)

        return deliveredNode
    }

    private fun addDeliveryIntentInternal(node : NodeModel) {
        if(node.map.isGtdActive && toAddDeliveryIntent) addDeliveryIntent(node)
    }

    fun addDeliveryIntent(node : NodeModel) {
        GtdIntentController.addIntent(node.gtdNode, deliveryIntent.copy())
    }

    // heap ***************************************************************************
    override fun onNodeInserted(parent: NodeModel, child: NodeModel, newIndex: Int) {
        if(parent.isMailNode) addDeliveryIntentInternal(child)
    }
}