package org.freeplane.features.gtd.layers

import org.freeplane.features.custom.map.*
import org.freeplane.features.custom.map.extensions.KIExtension
import org.freeplane.features.custom.map.extensions.SpreadsMap
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.gtd.layers.GtdLayerType.LayerLocal
import org.freeplane.features.gtd.layers.GcsType.*
import org.freeplane.features.gtd.module.GtdModule
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.node.GtdNodeModel

class GtdLayer() : KIExtension {
    override var spread : TreeSpread = TreeSpread.OWN

    var level : Int = 0

    var type : GtdLayerType = LayerLocal

    var gcsType : GcsType = LOCAL

    // level is not copied
    fun copy() : GtdLayer = GtdLayer().also {
        it.spread = spread
        it.type = type
        it.gcsType = gcsType
    }
}

fun SpreadsMap<GtdLayer>.deepCopy() : SpreadsMap<GtdLayer> {
    val result = SpreadsMap<GtdLayer>(GtdLayer::class)

    map.keys.forEach {
        result[it] = this[it]!!.copy()
    }

    return result
}

var GtdNodeModel.layerSpreadsMap : SpreadsMap<GtdLayer>
    get() = node.getSpreadsMap(GtdLayer::class)
    set(value) = node.putSpreadsMap(value)

fun GtdNodeModel.getLayer(spread : TreeSpread = TreeSpread.OWN) = layerSpreadsMap[spread]

fun GtdNodeModel.setLayer(layer : GtdLayer, spread: TreeSpread) {
    layerSpreadsMap = layerSpreadsMap.also { it[spread] = layer }
}

fun GtdNodeModel.containsLayer(spread: TreeSpread) = getLayer(spread) != null

val GtdNodeCover.layer : GtdLayer? get() =
    nodeCover.inheritedExtensions[GtdLayer::class]?.list?.firstOrNull() as GtdLayer?
        ?: gtdNodeModel.getLayer(TreeSpread.OWN)

val GtdModule.localLayerLevel get() = if(layer1ToExport) 2 else if(layer0ToExport) 1 else 0

val emptyLayerSpreadsMap get() = SpreadsMap<GtdLayer>(GtdLayer::class)

// exclude flag **********************************************************************************************
var GtdNodeModel.layerExcludeFlag : Boolean
    get() = node.containsNotToInheritExtension(GtdLayer::class)
    set(value) {
        if(value) node.addNotToInheritExtension(GtdLayer::class)
        else node.removeNotToInheritExtension(GtdLayer::class)
    }

// predefined layers
val GLOB_GCS = GtdLayer().apply {
    gcsType = GLOB
}

val MODULE_GCS = GtdLayer().apply {
    gcsType = MODULE
}