package org.freeplane.features.gtd.actions

import org.freeplane.features.gtd.map.cover.GtdMapCover
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import java.awt.event.ActionEvent
import javax.swing.ImageIcon

abstract class AGtdMapCoverAction : AGtdAction {
    constructor(key: String) : super(key)
    constructor(key: String, name: String?, imageIcon: ImageIcon? = null) : super(key, name, imageIcon)

    lateinit var gtdMapC : GtdMapCover

    override fun actionPerformed(e: ActionEvent?) {
        gtdMapC = GtdMapCoverController.currentGtdMapCover
        actionPerformed()
    }

    abstract fun actionPerformed()
}