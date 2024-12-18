package org.freeplane.features.custom.preprocess

import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.custom.treestream.*

object Preprocessor {
    fun preprocess() {
        val file = SpaceController.getMap("Balvanka")!!
        val xmlMap = XmlMapModel.create(file)

        val root = xmlMap.root

//        root.stream().forEachSpread("") {it, spread ->
//            "$spread ${it.text}".also { println(it) }
//        }

        root.stream().descendants.size.let { println(it) }
    }
}