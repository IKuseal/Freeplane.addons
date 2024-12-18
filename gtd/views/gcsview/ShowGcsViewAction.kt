package org.freeplane.features.gtd.views.gcsview

import org.freeplane.features.gtd.actions.AGtdNodeCoverAction
import org.freeplane.features.gtd.layers.*
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.views.GtdViewsController
import java.awt.event.ActionEvent

class ShowGcsViewAction(val strategy : GcsViewStrategy) : AGtdNodeCoverAction(generateKey(strategy), "$strategy") {
    enum class GcsViewStrategy {
        CLOSEST {
            override val find: (GtdNodeCover) -> Gcs
                get() = {
                    it.standardGcs ?: it.moduleGcs ?: it.matchedGcs
                }
        },
        MODULE {
            override val find: (GtdNodeCover) -> Gcs
                get() = { it.moduleGcs ?: it.matchedModuleGcs }
        },
        GLOB {
            override val find: (GtdNodeCover) -> Gcs
                get() = { it.gtdMapCover.globGcs }
        };

        abstract val find : (GtdNodeCover) -> Gcs
    }

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdViewsController.showGcsView(strategy.find(node))
    }

    companion object {
        fun generateKey(strategy : GcsViewStrategy) = "${ShowGcsViewAction::class.simpleName}.${strategy.name}"
    }
}