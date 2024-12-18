package org.freeplane.features.gtd.glob.globspace

import org.freeplane.features.custom.preprocess.XmlMapModel
import org.freeplane.features.custom.preprocess.XmlNodeModel
import org.freeplane.features.gtd.preprocess.gtdNode
import org.freeplane.features.gtd.preprocess.moduleRoots

object MapGlobSkeletonCalculator {
    // after calculation XmlMapModel
    // becomes invalid for further use
    fun calculate(map : XmlMapModel) {
        val moduleRoots = map.moduleRoots
        val mapRoot = map.root

        moduleRoots.forEach {
            ModuleGlobSkeletonCalculator.invoke(it)
        }

        if(mapRoot.gtdNode?.module == null) {
            moduleRoots.forEach(XmlNodeModel::remove)

            mapRoot.children.forEach(XmlNodeModel::remove)

            val mapRootXml = mapRoot.xml
            moduleRoots.map { it.xml }.forEach {
                mapRootXml.appendChild(it)
            }
        }
    }

    operator fun invoke(map : XmlMapModel) = calculate(map)
}