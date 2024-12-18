package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.actions.GTD_SELECTED_INTENT_EXISTS_PROP
import org.freeplane.features.gtd.data.elements.GtdSubState
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchGtdSubStateAction(val subState : GtdSubState)
    : AMultipleGtdNodeCoverAction(generateKey(subState), subState.clazz.name)
{
    init {
        registerEnabledProp(GTD_SELECTED_INTENT_EXISTS_PROP)
    }

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdCoverIntentController.putSubState(node, subStateToSet, subState.clazz)
    }

    override fun actionPerformed(e: ActionEvent?) {
        setSelected()
        super.actionPerformed(e)
    }

    val subStateToSet : GtdSubState? get() = subState.takeIf { !isSelected }

    override fun setSelected() {
        isSelected = !checkToMakeOn()
    }

    private fun checkToMakeOn() =
        !(GtdCoverIntentController.selectedIntent?.containsSubState(subState.clazz) ?: false)

    companion object {
        fun generateKey(subState: GtdSubState) = SwitchGtdSubStateAction::class.simpleName + "." + subState.clazz.name
    }
}