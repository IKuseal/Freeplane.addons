package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AMultipleGtdIntentCoverAction
import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.GtdNum
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.lowerState
import org.freeplane.features.gtd.data.elements.upperState
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class ShiftGtdStateAction(val isUpper : Boolean)
    : AMultipleGtdIntentCoverAction(generateKey(isUpper), "${isUpper.directionStr}-State", null) {

    val shifted : GtdState.() -> GtdState = if(isUpper) GtdState::upperState else GtdState::lowerState

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover, intentC: GtdNodeCover.GtdIntentCover) {
        runCatching {
            GtdCoverIntentController.setState(node, intentC.model, intentC.state.shifted())
        }
    }

    companion object {
        val Boolean.directionStr get() = if(this) "UPPER" else "LOWER"

        fun generateKey(isUpper : Boolean) =
            "Gtd.${ShiftGtdStateAction::class.simpleName}.${isUpper.directionStr}"
    }
}