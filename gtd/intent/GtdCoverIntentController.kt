package org.freeplane.features.gtd.intent

import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.*
import org.freeplane.features.gtd.intent.actions.*
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.map.cover.GtdMapCover
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.map.cover.actions.*
import org.freeplane.features.gtd.map.cover.onlycreatedintents.OnlyCreatedIntentsToTopHandler
import org.freeplane.features.gtd.node.GtdIntentCover
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.tag.*

object GtdCoverIntentController {
    val tagsToReset : Collection<GtdTag> = arrayListOf<GtdTag>().apply {
        add(GTD_TAG_ARTIFACT_REVIEW)
        add(GTD_TAG_REVIEW)
        add(GTD_TAG_SYNC_REVIEW)
        add(GTD_TAG_LIGHT_REVIEW)
        add(GTD_TAG_CHECK0)
        add(GTD_TAG_CHECK1)
        add(GTD_TAG_ARCHIVE)
        add(GTD_TAG_BOOKMARK)
        add(GTD_TAG_PERIODICAL)
        add(GTD_TAG_DIRECTION)
        add(GTD_TAG_COLLECTOR)
    }

    fun init() {
        GtdController.run {
            addAction(RemoveTopGtdIntentAction())
            addAction(CreateEmptyGtdIntentAction())
            addAction(CreateGtdIntentAction())
            addAction(CreateGtdIntentFromDefaultIntentAction())
            addAction(EnableSinglePlaneCreationParamAction())
            addUiBuilder(EnableTagCreationParamMenuBuilder.key, EnableTagCreationParamMenuBuilder)
            addAction(CreateDeliveryGtdIntentAction())
            addAction(ResetGtdIntentAction())
            addAction(HardResetGtdIntentAction())
            addAction(ShiftGtdStateAction(true))
            addAction(ShiftGtdStateAction(false))
            addAction(AddRawAiIntentAction())

            addUiBuilder(SET_GTD_STATE_CLASS_MENU_BUILDER, SetGtdStateClassMenuBuilder)
            addUiBuilder(SET_GTD_STATE_NUM_MENU_BUILDER, SetGtdStateNumMenuBuilder)
            addUiBuilder(SWITCH_GTD_SUB_STATE_MENU_BUILDER, SwitchGtdSubStateMenuBuilder)
            addUiBuilder(RemoveGtdIntentsWithTagMenuBuilder.key, RemoveGtdIntentsWithTagMenuBuilder)
            addUiBuilder(SetOrCreateAiGtdIntentWithLevelMenuBuilder.key, SetOrCreateAiGtdIntentWithLevelMenuBuilder)
        }
    }

    // intent ***********************************************************************************************
    fun createIntentWithTemplate(gtdMapC : GtdMapCover) =
        GtdIntent(tags = gtdMapC.tagCreationParamsAccessor.tagCreationParams)

    fun createTopIntent(gtdNodeC: GtdNodeCover, template : GtdIntent? = null) =
        gtdNodeC.intent?.model
            ?: template?.copy() ?: createIntentWithTemplate(gtdNodeC.gtdMapCover)
                .also {
                    GtdIntentController.addIntent(gtdNodeC.gtdNodeModel, it)
                }

    fun removeTopIntent(gtdNodeC: GtdNodeCover) {
        val intent = gtdNodeC.intent?.model ?: return
        GtdIntentController.removeIntent(gtdNodeC.gtdNodeModel, intent)
    }

    fun clearIntents(gtdNodeC: GtdNodeCover) {
        val gtdNode = gtdNodeC.gtdNodeModel
        gtdNodeC.intents.map { it.model }.forEach {
            if(gtdNode.containsIntent(it))
                GtdIntentController.removeIntent(gtdNode, it)
        }
    }

    fun resetIntent(gNodeC: GtdNodeCover, intentC : GtdIntentCover) {
        val gNodeM = gNodeC.gtdNodeModel
        val intentM = intentC.model
        val tagsToReset = intentM.tags.filter(tagsToReset::contains)

        GtdIntentController.setGcsType(gNodeM, intentM, GcsType.LOCAL)
        tagsToReset.forEach {
            GtdIntentController.removeTag(gNodeM, intentM, it)
        }
    }

    // state ************************************************************************************************
    fun setState(gtdNodeC: GtdNodeCover, intent: GtdIntent, state: GtdState) {
        GtdIntentController.setState(gtdNodeC.gtdNodeModel, intent, state)
    }

    fun setStateCreateOnNoIntent(gtdNodeC: GtdNodeCover, state: GtdState, template : GtdIntent? = null) {
        val intent = createTopIntent(gtdNodeC, template)

        GtdIntentController.setState(gtdNodeC.gtdNodeModel, intent, state)
    }

    fun setStateClassCreateOnNoIntent(gtdNodeC: GtdNodeCover, stateClass: GtdStateClass) {
        var intent : GtdIntent = createTopIntent(gtdNodeC)

        GtdIntentController.setStateClass(gtdNodeC.gtdNodeModel, intent, stateClass)
    }

    fun setStateNum(gtdNodeC: GtdNodeCover, num : GtdNum) {
        val intent = gtdNodeC.intentModel ?: return
        GtdIntentController.setStateNum(gtdNodeC.gtdNodeModel, intent, num)
    }

    // subStates **********************************************************************************************
    fun putSubState(gtdNodeC : GtdNodeCover, subState : GtdSubState?,
                    clazz : GtdSubStateClass = subState!!.clazz) {
        val intent = gtdNodeC.intentModel ?: return
        GtdIntentController.putSubState(gtdNodeC.gtdNodeModel, intent, subState, clazz)
    }

    // * * *
    val selectedIntent : GtdIntent?
        get() = GtdMapCoverController.selectedGtdNodeCoverSafe?.intentModel
}

val controller get() = GtdCoverIntentController

fun GtdNodeCover.createNewIntentUseTemplate(moveToTop : Boolean = false) =
    controller.createIntentWithTemplate(gtdMapCover).also {
        GtdIntentController.addIntent(gtdNodeModel, it)
        if(moveToTop)
            OnlyCreatedIntentsToTopHandler.addNodeWithOnlyCreatedIntent(this, it)
    }

fun GtdNodeCover.createNewEmptyIntent(moveToTop : Boolean = false) =
    GtdIntent().also {
        GtdIntentController.addIntent(gtdNodeModel, it)
        if(moveToTop)
            OnlyCreatedIntentsToTopHandler.addNodeWithOnlyCreatedIntent(this, it)
    }