package org.freeplane.features.custom.space

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.url.mindmapmode.MapLoader
import org.freeplane.view.swing.features.custom.space.NavigateToMapInSpaceDialog
import java.awt.event.ActionEvent

class NavigateToMapInSpaceAction() : AFreeplaneAction("Custom.NavigateToMapInSpaceAction") {
    private lateinit var dialog : NavigateToMapInSpaceDialog

    override fun actionPerformed(e: ActionEvent?) {
        dialog = NavigateToMapInSpaceDialog(
            GlobalFrFacade.frame,
            SpaceController.maps,
            ::onResult
        ).apply {
            pack()
            setLocation(frame.width/2 - width/2,
                frame.height/2 - height/2)
        }

        dialog!!.show()
    }

    private fun onResult(mapFile: MapFile) {
        dialog.run {
            isVisible = false
            dispose()
        }

        MapLoader(GlobalFrFacade.modeController).run {
            load(mapFile.file)
            withView()
            saveAfterLoading()
            map
        }
    }
}