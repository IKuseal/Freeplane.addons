package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AGtdMapCoverAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import javax.swing.JOptionPane

class SetTotalCoverageModeStateAction : AGtdMapCoverAction("Gtd.SetTotalCoverageModeStateAction") {
    override fun actionPerformed() {
        var stateKey = JOptionPane.showInputDialog(
            GtdController.frame,
            "Write a state to use",
            "Total Coverage Mode",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            gtdMapC.totalCoverageModeState?.name ?: "") as String?

        stateKey ?: return

        val state = if(stateKey.isNotEmpty()) {
            try {
                GtdState.valueOf(stateKey)
            }
            catch (exception : Throwable) {
                // nothing required
                return
            }
        } else null

        GtdMapCoverController.setTotalCoverageModeState(gtdMapC, state)
    }
}