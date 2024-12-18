package org.freeplane.features.gtd.readwrite

import org.freeplane.core.io.IElementDOMHandler
import org.freeplane.core.io.ReadManager
import org.freeplane.features.gtd.data.elements.*
import org.freeplane.features.gtd.data.elements.GtdNum.*
import org.freeplane.features.gtd.data.elements.GtdStateClass.*
import org.freeplane.features.gtd.data.elements.GtdSubStateClass.*
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.tag.GtdTag
import org.freeplane.features.gtd.tag.extWorkBuiltinTagsByName
import org.freeplane.n3.nanoxml.XMLElement
import java.io.IOException

object GtdIntentBuilder : IElementDOMHandler {
    const val TAG = "gtdIntent"

    const val TAGS_ATTR = "tags"
    private const val STATE_ATTR = "state"
    private const val SUBSTATES_ATTR = "substates"
    const val GCS_TYPE_ATTR = "gcsType"

    private const val D = ","

    fun registerBy(reader: ReadManager) {
        reader.addElementHandler(TAG, this)
        registerAttributeHandlers(reader)
    }

    @Throws(IOException::class)
    fun writeIntent(parentXml : XMLElement, intent : GtdIntent) {
//        if(intent.isEmpty) return TODO() am I right?

        val extXml = XMLElement()

        extXml.name = TAG

//        extXml.setAttribute(PLANE_ATTR, intent.plane.name)
        intent.tags.joinToString(",") { it.name }
            .takeIf { it.isNotEmpty() }
            ?.let { extXml.setAttribute(TAGS_ATTR, it) }
        extXml.setAttribute(STATE_ATTR, intent.state.name)
        extXml.setAttribute(GCS_TYPE_ATTR, intent.gcsType.name)

        intent.subStates.takeIf { it.isNotEmpty() }?.let {
            extXml.setAttribute(SUBSTATES_ATTR, it.joinToString(D) { it.name })}

        parentXml.addChild(extXml)
    }

    override fun createElement(parent: Any?, tag: String?, attributes: XMLElement?): Any {
        return GtdIntent()
    }

    override fun endElement(parent: Any?, tag: String?, element: Any?, dom: XMLElement?) {
        val node = parent as GtdNodeModel
        val intent = element as GtdIntent
        node.addIntent(intent)
    }

    private fun registerAttributeHandlers(reader: ReadManager) {
        reader.addAttributeHandler(TAG, TAGS_ATTR)
        { userObject, value -> (userObject as GtdIntent).addTags(
            (value as String).split(",").map { extWorkBuiltinTagsByName[it]!! }
//            (value as String).split(",").mapNotNull { mmMigrateTag(it) }
        ) }

        reader.addAttributeHandler(TAG, STATE_ATTR)
//        { userObject, value -> (userObject as GtdIntent).state = GtdState.valueOf(value) }
        { userObject, value -> (userObject as GtdIntent).state = mmMigrateState(value) }

        reader.addAttributeHandler(TAG, GCS_TYPE_ATTR)
        { userObject, value -> (userObject as GtdIntent).gcsType = GcsType.valueOf(value) }

        reader.addAttributeHandler(TAG, SUBSTATES_ATTR)
        { userObject, value ->
            val intent = userObject as GtdIntent

            value.split(D).forEach{intent.putSubState(GtdSubState.valueOf(it))}
//            value.split(D).forEach{
//                mmMigrateSubState(it)?.let(intent::putSubState)
//            }
        }
    }

    private fun mmMigrateState(key : String) : GtdState =
        when(key) {
            "R:NO" -> R(S5)
            else -> GtdState.valueOf(key)
        }

//    private fun mmMigrateTag(key : String) : GtdTag? =
//        when(key) {
//            "Temp" -> null
//            else -> extWorkBuiltinTagsByName[key]!!
//        }

//    private fun mmMigrateSubState(key : String) : GtdSubState? {
//        val subState = GtdSubState.valueOf(key)
//        return when(subState.clazz) {
//            ZIP, RV -> null
//            else -> subState
//        }
//    }

//    private fun mmMigrateState(state : GtdState) : GtdState {
//        val clazz = state.clazz
//        val num = state.num
//        return when(clazz) {
//            Q -> when(num) {
//                S0, S1, S2 -> Q0(mmMNum3(num))
//                S3, S4, S5, S6 -> Q1(mmMNum3(num))
//                S7, S8, S9 -> Q2(mmMNum3(num))
//                else -> Q0(num)
//            }
//            U -> when(num) {
//                S0, S1, S2, S3, S4 -> U0(mmMNum2(num))
//                S5, S6, S7, S8, S9 -> U2(mmMNum2(num))
//                else -> U0(num)
//            }
//            O -> when(num) {
//                S0, S1, S2 -> E0(mmMNum3(num))
//                S3, S4, S5, S6 -> E1(mmMNum3(num))
//                S7, S8, S9 -> E2(mmMNum3(num))
//                else -> E0(num)
//            }
//            W -> when(num) {
//                S0, S1, S2 -> W0(mmMNum3(num))
//                S3, S4, S5, S6 -> W1(mmMNum3(num))
//                S7, S8, S9 -> W2(mmMNum3(num))
//                else -> W0(num)
//            }
//            Y -> when(num) {
//                S0, S1, S2, S3, S4 -> Y0(mmMNum2(num))
//                S5, S6, S7, S8, S9 -> Y2(mmMNum2(num))
//                else -> Y0(num)
//            }
//            else -> state
//        }
//    }

//    private fun mmMNum3(num : GtdNum) = when(num) {
//        S0 -> S0
//        S1 -> S1
//        S2 -> S2
//        S3 -> S0
//        S4 -> S1
//        S5 -> S2
//        S6 -> S3
//        S7 -> S0
//        S8 -> S1
//        S9 -> S2
//        else -> num
//    }

//    private fun mmMNum2(num : GtdNum) = when(num) {
//        S0 -> S0
//        S1 -> S1
//        S2 -> S2
//        S3 -> S3
//        S4 -> S4
//        S5 -> S0
//        S6 -> S1
//        S7 -> S2
//        S8 -> S3
//        S9 -> S4
//        else -> num
//    }
}