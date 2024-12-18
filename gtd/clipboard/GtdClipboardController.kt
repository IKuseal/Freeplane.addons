package org.freeplane.features.gtd.clipboard

import org.freeplane.features.gtd.clipboard.actions.CopyGtdNodeAction
import org.freeplane.features.gtd.clipboard.actions.PasteGtdGStateAction
import org.freeplane.features.gtd.clipboard.actions.PasteGtdIntentAction
import org.freeplane.features.gtd.clipboard.actions.PasteGtdNodeAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.GtdSubState
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.intent.GtdIntentController
import org.freeplane.features.gtd.intent.createNewIntentUseTemplate
import org.freeplane.features.gtd.layers.GtdLayerController
import org.freeplane.features.gtd.layers.deepCopy
import org.freeplane.features.gtd.layers.layerExcludeFlag
import org.freeplane.features.gtd.layers.layerSpreadsMap
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.map.cover.onlycreatedintents.OnlyCreatedIntentsToTopHandler
import org.freeplane.features.gtd.module.GtdModuleController
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.currentCover

object GtdClipboardController {
    fun init() {
        GtdController.run {
            addAction(PasteGtdGStateAction())
            addAction(CopyGtdNodeAction())
            addAction(PasteGtdNodeAction())
            addAction(PasteGtdIntentAction())
        }
    }

    private lateinit var gtdNodeToCopy : GtdNodeModel
    private lateinit var stateToCopy : GtdState
    private lateinit var subStatesToCopy : Collection<GtdSubState>
    private var intentToCopy : GtdIntent? = null

    // intents are copied only in original version
    // states & subStates if separately that in shown version
    // gtdNodeToCopy -> original version with filtering on active intents
    fun copy(gtdNodeC : GtdNodeCover) {
        val topIntent = gtdNodeC.intentOrFake
        stateToCopy = topIntent.state
        subStatesToCopy = topIntent.subStates.toList()
        intentToCopy = gtdNodeC.intent?.model?.copy()
        gtdNodeToCopy = gtdNodeC.copy()
    }

    fun deepCopy(gtdNodeC: GtdNodeCover) {
        copy(gtdNodeC)
        gtdNodeToCopy = gtdNodeC.gtdNodeModel.copy()
    }

    fun pasteGState(gtdNodeC: GtdNodeCover) {
        if(!::stateToCopy.isInitialized) return

        val gtdNode = gtdNodeC.gtdNodeModel
        val gcic = GtdCoverIntentController
        val gic = GtdIntentController

        val intent = GtdCoverIntentController.createTopIntent(gtdNodeC)

        gcic.setState(gtdNodeC, intent, stateToCopy)
        gic.clearSubStates(gtdNode, intent)
        subStatesToCopy.forEach {
            gic.putSubState(gtdNode, intent, it)
        }
    }

    fun pasteIntent(node : GtdNodeModel) {
        if(intentToCopy == null) return

        val intent = intentToCopy!!.copy()
        GtdIntentController.addIntent(node, intent)
        OnlyCreatedIntentsToTopHandler.addNodeWithOnlyCreatedIntent(node.currentCover, intent)
    }

    fun pasteIntents(gtdNode : GtdNodeModel) {
        if(!::gtdNodeToCopy.isInitialized) return

        // reset
        GtdIntentController.clearIntents(gtdNode)

        // paste
        gtdNodeToCopy.intents.forEach {
            GtdIntentController.addIntent(gtdNode, it.copy())
        }
    }

    fun paste(gtdNode: GtdNodeModel) {
        if(!::gtdNodeToCopy.isInitialized) return

        GtdMapController.resetNode(gtdNode)

        gtdNodeToCopy.intents.forEach {
            GtdIntentController.addIntent(gtdNode, it.copy())
        }

        GtdModuleController.setGtdModule(gtdNode, gtdNodeToCopy.module?.copy())

        GtdLayerController.setSpreadsMap(gtdNode, gtdNodeToCopy.layerSpreadsMap.deepCopy())
        if(gtdNodeToCopy.layerExcludeFlag) GtdLayerController.switchExcludeFlag(gtdNode, true)
    }
}