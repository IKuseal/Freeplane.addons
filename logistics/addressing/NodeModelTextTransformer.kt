package org.freeplane.features.logistics.addressing

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.icon.MindIcon
import org.freeplane.features.map.NodeModel

class NodeModelTextTransformer(val node : NodeModel) {
//    operator fun getValue(thisRef : Any, prop : KProperty<*>) : String {
//        return node.text.split(" ")[0]
//    }

    fun transform() = node.transformedText

    private val NodeModel.transformedText get() =
        if(text.isNotEmpty()) textFromCoreText
        else if(GlobalFrFacade.getIconsStandard(this).isNotEmpty()) textFromIcons
        else ""

    private val NodeModel.textFromCoreText get() = text
        .split(" ", "\n")
        .take(3)
        .map { it.split("-", ".") }
        .flatten()
        .joinToString(" ")

    private val NodeModel.textFromIcons get() = extIcons
        .mapNotNull { if(it is MindIcon) it else null }
        .map { it.translatedDescription }
        .filterNot { it == "postbox" }
        .joinToString(" ")

    private val NodeModel.extIcons get() = GlobalFrFacade.getIconsStandard(this)
}