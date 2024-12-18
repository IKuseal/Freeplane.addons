package org.freeplane.features.custom

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.anki.aiFreeplane.cards.MAiCardController
import org.freeplane.features.anki.core.AiCardOperator
import org.freeplane.features.anki.data_providing.AiProviderProxy
import org.freeplane.features.ankikt.dataproviding.AiCardReviewArgs
import org.freeplane.features.ankikt.dataproviding.ReviewType
import org.freeplane.features.custom.historynavigation.edithistorynavigation.EditHistoryNavigationController
import org.freeplane.view.swing.features.custom.gtd.test.TestDialog
import java.awt.event.ActionEvent
import javax.swing.JOptionPane

class TestFunctionalityAction : AFreeplaneAction("Custom.TestFunctionalityAction") {
    lateinit var dialog: TestDialog

    override fun actionPerformed(e: ActionEvent?) {
        val navigator = EditHistoryNavigationController.navigator

        GlobalFrFacade.selectedNodes.forEach(navigator::add)
    }


    fun history() {
        val provider = AiProviderProxy.INSTANCE.internalProviderToTest;

//        System.out.println("Timestamp" + System.currentTimeMillis()/1000)

//        provider.insertReviews(listOf(MAiCardController.getController().selectedCard.id), listOf())

        val newIvlStr = JOptionPane.showInputDialog(
            GlobalFrFacade.frame,
            "Type new interval: ",
            "Answering card",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null) as? String


        val newIvl = newIvlStr?.toIntOrNull() ?: return
        val reviewType = if(newIvl < 0) ReviewType.LEARN_TYPE else ReviewType.REVIEW_TYPE

        val cards = listOf(MAiCardController.getController().selectedCard.id)

        AiCardOperator.insertReviews(listOf(MAiCardController.getController().selectedCard),
            listOf(AiCardReviewArgs(newIvl, reviewType)))

        provider.cardsLatestReviewsInfo(cards)

        provider.forDevPurposesCards(cards)
    }
}