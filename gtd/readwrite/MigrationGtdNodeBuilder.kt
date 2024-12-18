package org.freeplane.features.gtd.readwrite

object MigrationGtdNodeBuilder
//    : IElementDOMHandler
{
//    const val TAG_NAME = "gtdData"
//    private const val STATE_ATTR = "STATE"
//    private const val STATE_ATTR_OWN = "${STATE_ATTR}_OWN"
//    private val plane = GTD_INTENT_PLANE_DEFAULT
//    private const val STATE_ATTR_DESC = "${STATE_ATTR}_DESC"
//    private const val SUBSTATES_ATTR = "SUBSTATES"
//    private const val SUBSTATES_ATTR_OWN = "${SUBSTATES_ATTR}_OWN"
//    private const val SUBSTATES_ATTR_DESC = "${SUBSTATES_ATTR}_DESC"
//
//
//    fun registerBy(reader: ReadManager) {
//        reader.addElementHandler(TAG_NAME, this)
//        registerAttributeHandlers(reader)
//    }
//
//    override fun createElement(parent: Any, tag: String?, attributes: XMLElement?) =
//        (parent as NodeModel).gtdNode
//
//    override fun endElement(parent: Any?, tag: String?, userObject: Any?, dom: XMLElement?) {
//    }
//
//    private fun registerAttributeHandlers(reader: ReadManager) {
//        reader.addAttributeHandler(TAG_NAME, STATE_ATTR_OWN) { userObject, value ->
//            (userObject as GtdNodeModel).createIntent(plane).state = readState(value) ?: return@addAttributeHandler
//        }
//
//        reader.addAttributeHandler(TAG_NAME, STATE_ATTR_DESC) { userObject, value ->
//            (userObject as GtdNodeModel).setSpreadState(
//                readState(value) ?: return@addAttributeHandler, DESCENDANT)
//        }
//
//        reader.addAttributeHandler(TAG_NAME, SUBSTATES_ATTR_OWN) { userObject, value ->
//            (userObject as GtdNodeModel).createIntent(plane).putAllSubStates(readSubStates(value))
//        }
//
//        reader.addAttributeHandler(TAG_NAME, SUBSTATES_ATTR_DESC) { userObject, value ->
//            userObject as GtdNodeModel
//            readSubStates(value).forEach {
//                userObject.setSpreadSubState(it, DESCENDANT)
//            }
//        }
//    }
//
//    private fun readState(value : String) =
//        value.takeIf { it.isNotEmpty() }?.let { GtdState.valueWithOldName(it) }
//
//    private fun readSubStates(str : String) : Collection<GtdSubState> {
//        if(str.isEmpty()) return emptyList()
//        return str.split(" ").map { GtdSubState.valueWithOldName(it) }
//    }
}

//fun GtdState.Companion.valueWithOldName(name : String) = gtdStatusByOldName[name] as GtdState
//
//fun GtdSubState.Companion.valueWithOldName(name : String) = gtdStatusByOldName[name] as GtdSubState
//
//val gtdStatusByOldName = hashMapOf<String, GtdStatus>().apply {
//    GtdStateClass.values().forEach {
//        GtdState.values(it).forEach {
//            this[it.oldName] = it
//        }
//    }
//
//    GtdSubStateClass.values().forEach {
//        GtdSubState.values(it).forEach {
//            this[it.oldName] = it
//        }
//    }
//}
//
//val GtdStatus.oldName : String get() = "${clazz.name}${num.oldName}"
//
//val GtdNum.oldName : String get() = when(this) {
//    NO -> ""
//    else -> toString()
//}