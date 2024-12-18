package org.freeplane.features.gtd.intent

import org.freeplane.core.undo.IActor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.*
import org.freeplane.features.gtd.intent.actions.EnableGtdTagMenuBuilder
import org.freeplane.features.gtd.intent.actions.SetGcsTypeToIntentMenuBuilder
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.tag.GtdTag

object GtdIntentController {
    fun init() {
        GtdController.run {
            addUiBuilder(SetGcsTypeToIntentMenuBuilder.key, SetGcsTypeToIntentMenuBuilder)
            addUiBuilder(EnableGtdTagMenuBuilder.key, EnableGtdTagMenuBuilder)
        }
    }

    // intent ***********************************************************************************************
    fun addIntent(gtdNode : GtdNodeModel, intent : GtdIntent) {
        if(gtdNode.containsIntent(intent)) return

        val actor = object : IActor {
            override fun act() {
                gtdNode.addIntent(intent)
                GtdMapController.fireNodeChange(gtdNode)
            }

            override fun getDescription() = "add GtdIntent to GtdNode"

            override fun undo() {
                gtdNode.removeIntent(intent)
                GtdMapController.fireNodeChange(gtdNode)
            }
        }

        execute(actor, gtdNode)
    }

    fun removeIntent(gtdNode : GtdNodeModel, intent : GtdIntent) {
        val intent = gtdNode.getIntent(intent) ?: return

        val actor = object : IActor {
            override fun act() {
                gtdNode.removeIntent(intent)
                GtdMapController.fireNodeChange(gtdNode)
            }

            override fun getDescription() = "remove GtdIntent from GtdNode"

            override fun undo() {
                gtdNode.addIntent(intent)
                GtdMapController.fireNodeChange(gtdNode)
            }
        }

        execute(actor, gtdNode)
    }

    fun clearIntents(gtdNode: GtdNodeModel) {
        gtdNode.intents.forEach {
            removeIntent(gtdNode, it)
        }
    }

    // state ***********************************************************************************************
    fun setStateClass(node : GtdNodeModel, intent: GtdIntent, clazz: GtdStateClass) {
        if(intent.state.clazz == clazz) return
        setState(node, intent, clazz.default)
    }

    fun setStateNum(node : GtdNodeModel, intent: GtdIntent, num : GtdNum) {
        val state = intent.state.takeIf { it.clazz.isNumValid(num) } ?: return
        val newState = state.setNum(num)
        setState(node, intent, newState)
    }

    fun setState(node : GtdNodeModel, intent: GtdIntent, state : GtdState) {
        if(intent.state == state) return

        val oldState = intent.state

        val actor = object : IActor {
            override fun act() {
                intent.state = state
                GtdMapController.fireNodeChange(node)
            }

            override fun getDescription() = "setting GtdIntent State"

            override fun undo() {
                intent.state = oldState
                GtdMapController.fireNodeChange(node)
            }
        }

        execute(actor, node)
    }

    // subStates *******************************************************************************************
    fun putSubState(node : GtdNodeModel, intent: GtdIntent, subState : GtdSubState?,
                    clazz : GtdSubStateClass = subState!!.clazz) {
        val oldSubState = intent.getSubState(clazz)

        if(oldSubState == subState) return

        val actor = object : IActor {
            override fun act() {
                subState?.also { intent.putSubState(it) } ?: intent.removeSubState(clazz)
                GtdMapController.fireNodeChange(node)
            }

            override fun getDescription() = "put SubState to GtdIntent"

            override fun undo() {
                oldSubState?.also { intent.putSubState(it) } ?: intent.removeSubState(clazz)
                GtdMapController.fireNodeChange(node)
            }
        }

        execute(actor, node)
    }

    fun clearSubStates(gtdNode : GtdNodeModel, intent: GtdIntent) {
        intent.subStates.forEach {
            putSubState(gtdNode, intent, null, it.clazz)
        }
    }

    // tags ************************************************************************************
    fun addTag(node: GtdNodeModel, intent: GtdIntent, tag : GtdTag) {
        enableTag(node, intent, tag)
    }

    fun removeTag(node: GtdNodeModel, intent: GtdIntent, tag : GtdTag) {
        enableTag(node, intent, tag, false)
    }

    fun enableTag(node : GtdNodeModel, intent: GtdIntent, tag : GtdTag, toEnable : Boolean = true) {
        if(intent.containsTag(tag) == toEnable) return

        val act : GtdIntent.(GtdTag) -> Unit
        val undo : GtdIntent.(GtdTag) -> Unit
        if(toEnable) {
            act = GtdIntent::addTag
            undo = GtdIntent::removeTag
        }
        else {
            act = GtdIntent::removeTag
            undo = GtdIntent::addTag
        }

        val actor = object : IActor {
            override fun act() {
                intent.act(tag)
                GtdMapController.fireNodeChange(node)
            }

            override fun getDescription() = "GtdIntent switch tag"

            override fun undo() {
                intent.undo(tag)
                GtdMapController.fireNodeChange(node)
            }
        }

        execute(actor, node)
    }

    // gcs-type ***********************************************************************************************
    fun setGcsType(node: GtdNodeModel, intent: GtdIntent, gcsType: GcsType) {
        val oldGcsType = intent.gcsType.takeIf { it != gcsType } ?: return

        val actor = object : IActor {
            override fun act() {
                intent.gcsType = gcsType
                GtdMapController.fireNodeChange(node)
            }

            override fun getDescription() = "set gcsType of GtdIntent"

            override fun undo() {
                intent.gcsType = oldGcsType
                GtdMapController.fireNodeChange(node)
            }
        }

        execute(actor, node)
    }

    // heap ***********************************************************************************************

    private fun execute(actor : IActor, gtdNode : GtdNodeModel) {
        GtdController.modeController.execute(actor, gtdNode.node.map)
    }
}