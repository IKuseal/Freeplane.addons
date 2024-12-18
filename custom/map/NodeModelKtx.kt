package org.freeplane.features.custom.map

import org.freeplane.core.extension.IExtension
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.map.extensions.SpreadsMap
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.custom.map.extensions.TreeSpread.*
import org.freeplane.features.custom.map.extensions.WrapperSpreadsMap
import org.freeplane.features.custom.treestream.forEach
import org.freeplane.features.custom.treestream.forEachBiDirectional
import org.freeplane.features.custom.treestream.forEachWithSpread
import org.freeplane.features.custom.treestream.nextAncestor
import org.freeplane.features.link.LinkController
import org.freeplane.features.link.mindmapmode.MLinkController
import org.freeplane.features.map.NodeModel
import org.freeplane.features.styles.IStyle
import java.net.URI
import kotlin.reflect.KClass

// extensions *******************************************************************************************
val NodeModel.ownExtensions
    get() = sharedData.ownExtensions!!

val NodeModel.childExtensions
    get() = sharedData.childExtensions!!

val NodeModel.descendantExtensions
    get() = sharedData.descendantExtensions!!

fun NodeModel.putExt(extension: IExtension, spread: TreeSpread = OWN): IExtension? {
    return putExt(extension::class, extension, spread)
}

fun NodeModel.putExt(clazz: KClass<out IExtension>, extension: IExtension?,
                     spread: TreeSpread = OWN): IExtension? {
    extension?.let { it.treeSpread = spread }

    return when(spread) {
        OWN -> ownExtensions.putExtension(clazz.java, extension)
        CHILD -> childExtensions.putExtension(clazz.java, extension)
        DESCENDANT -> descendantExtensions.putExtension(clazz.java, extension)
        TREE -> {
            ownExtensions.putExtension(clazz.java, extension)
            descendantExtensions.putExtension(clazz.java, extension)
        }
    }
}

fun NodeModel.getExtNoCast(clazz: KClass<out IExtension>, spread: TreeSpread = OWN): IExtension? =
    when(spread) {
        OWN -> ownExtensions.getExtensionNoCast(clazz.java)
        CHILD -> childExtensions.getExtensionNoCast(clazz.java)
        DESCENDANT -> descendantExtensions.getExtensionNoCast(clazz.java)
        TREE -> {
            ownExtensions.getExtensionNoCast(clazz.java)
            descendantExtensions.getExtensionNoCast(clazz.java)
        }
    }

fun <T : IExtension> NodeModel.getExt(clazz: KClass<T>, spread: TreeSpread = OWN): T? =
    getExtNoCast(clazz, spread) as T?


fun NodeModel.containsExt(clazz: KClass<out IExtension>, spread: TreeSpread = OWN) =
    when(spread) {
        OWN -> ownExtensions.containsExtension(clazz.java)
        CHILD -> childExtensions.containsExtension(clazz.java)
        DESCENDANT -> descendantExtensions.containsExtension(clazz.java)
        TREE -> throw IllegalArgumentException()
    }

fun <T : IExtension> NodeModel.getSpreadsMap(clazz: KClass<out IExtension>) : SpreadsMap<T> {
    val spreadsMap = SpreadsMap<T>(clazz)
    validSpreads.forEach { spread ->
        var ext = getExtNoCast(clazz, spread) as T?
        ext?.let { spreadsMap[spread] = ext }
    }
    return spreadsMap
}

fun <T : Any> NodeModel.getWrapperSpreadsMap(clazz: KClass<out IExtension>) : WrapperSpreadsMap<T> {
    return WrapperSpreadsMap(getSpreadsMap(clazz))
}

fun NodeModel.putSpreadsMap(spreadsMap: SpreadsMap<*>) {
    val clazz = spreadsMap.clazz
    validSpreads.forEach { spread ->
        var ext = spreadsMap[spread]
        putExt(clazz, ext, spread)
    }
}

val validSpreads = listOf(OWN, CHILD, DESCENDANT)

val NodeModel.notToInheritExtensions
    get() = sharedData.notToInheritExtensions!!

fun NodeModel.addNotToInheritExtension(clazz: KClass<out IExtension>)
= sharedData.notToInheritExtensions.add(clazz.java)

fun NodeModel.removeNotToInheritExtension(clazz: KClass<out IExtension>)
        = sharedData.notToInheritExtensions.remove(clazz.java)

fun NodeModel.containsNotToInheritExtension(clazz: KClass<out IExtension>)
        = sharedData.notToInheritExtensions.contains(clazz.java)

// tree-stream *****************************************************************************
fun <S> NodeModel.forEachWithSpread(initial : S, depth : Int = Int.MAX_VALUE, action : NodeModel.(S) -> S) {
    this.forEachWithSpread(NodeModel::getChildren, initial, depth, action)
}

fun NodeModel.forEach(depth : Int = Int.MAX_VALUE, action : NodeModel.() -> Unit) {
    this.forEach(NodeModel::getChildren, depth, action)
}

fun NodeModel.nextAncestor(condition : (NodeModel) -> Boolean) : NodeModel? {
    return this.nextAncestor(NodeModel::getParentNode, condition)
}

fun <S, U> NodeModel.forEachBiDirectional(
    upInitial : S,
    upAction : NodeModel.(S) -> Pair<Boolean, S>,
    downAction : NodeModel.(List<Pair<NodeModel, U>>) -> U,
    depth : Int = Int.MAX_VALUE) : U?
{
    return forEachBiDirectional(NodeModel::getChildren, upInitial, upAction, downAction, depth)
}

// relations ***************************************************************************************
val NodeModel.rootNode : NodeModel
    get() = map.rootNode!!

val NodeModel.branchRoot get() = nextAncestor { it.parentNode == null } ?: this

val NodeModel.isAttachedToMapTree get() = branchRoot === rootNode

fun NodeModel.removeChild(node : NodeModel) {
    remove(getIndex(node))
}

fun NodeModel.clearChildren() {
    children().toList().forEach {
        removeChild(it)
    }
}

// heap ***************************************************************************************
val NodeModel.currentNodeCover : NodeCover
    get() = createNodeCover(map.currentMapCover)

fun NodeModel.applyStyle(style : IStyle) {
    GlobalFrFacade.logicalStyleController.setStyle(this, style)
}

fun NodeModel.setRelativeLinkToNode(linkTo : NodeModel) {
    val mapFileName = linkTo.map.file?.name ?: return
    setRelativeLinkToNode(mapFileName, linkTo.id)
}

fun NodeModel.setRelativeLinkToNode(mapFileName : String, nodeId : String) {
    setRelativeLinkToNode("$mapFileName#$nodeId")
}

fun NodeModel.setRelativeLinkToNode(link : String) {
    val linkController = LinkController.getController() as MLinkController
    linkController.setLink(this, URI(link), LinkController.LINK_RELATIVE_TO_MINDMAP)
}