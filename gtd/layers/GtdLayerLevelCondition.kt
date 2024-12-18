package org.freeplane.features.gtd.layers

import java.lang.IllegalArgumentException

class GtdLayerLevelCondition(val level : Int) : GtdLayerCondition() {
    override val label: String
        get() = "layer level = $level"

    override fun check(layer: GtdLayer) =
        layer.level == level

    companion object {
        fun create(level : Int) = allLayerLevelConditions[level] ?: throw IllegalArgumentException()
    }
}

val allLayerLevelConditions : Map<Int, GtdLayerLevelCondition> = hashMapOf<Int, GtdLayerLevelCondition>().apply {
    for (i : Int in 0..MAX_LAYER_LEVEL) put(i, GtdLayerLevelCondition(i))
}

