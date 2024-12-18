package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AGtdMapCoverAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import javax.swing.JOptionPane

class SetTotalCoverageModeGcsTypeAction : AGtdMapCoverAction("Gtd.SetTotalCoverageModeGcsTypeAction") {
    override fun actionPerformed() {
        var gcsTypeKey = JOptionPane.showInputDialog(
            GtdController.frame,
            "Write a GCS-type to use",
            "Total Coverage Mode",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            gtdMapC.totalCoverageModeGcsType?.name ?: "") as String?

        gcsTypeKey ?: return

        val gcsType = if(gcsTypeKey.isNotEmpty()) {
            try {
                GcsType.valueOf(gcsTypeKey)
            }
            catch (exception : Throwable) {
                // nothing required
                return
            }
        } else null

        GtdMapCoverController.setTotalCoverageModeGcsType(gtdMapC, gcsType)
    }
}