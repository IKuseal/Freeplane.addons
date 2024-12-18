package org.freeplane.features.gtd.glob.globspace

import org.freeplane.features.custom.preprocess.XmlMapModel
import org.freeplane.features.custom.preprocess.XmlNodeModel
import org.freeplane.features.custom.preprocess.stream
import org.freeplane.features.custom.treestream.*
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.preprocess.gtdNode
import org.freeplane.features.gtd.tag.GTD_PLANE_DEFAULT
import org.freeplane.features.gtd.tag.withInherited

object ModuleGlobSkeletonCalculator {
    fun calculate(root : XmlNodeModel) {
        val mark = root.generateMark()
        markFirstStage(root, mark)
        root.stream().fillMark(mark)
        root.stream().cutNotMark(mark)
    }

    private fun markFirstStage(root: XmlNodeModel, mark : String) {
        val initialPredicate : (XmlNodeModel) -> Boolean = { it.gtdNode?.layer == null }

        root.stream().mark(mark) {
            if(initialPredicate(it)) true
            else {
                markExported(it, mark)
                isStoppedChildren = true
                true
            }
        }
    }

    private fun markExported(root : XmlNodeModel, mark : String) {
        val gcsTypes = arrayOf(GcsType.GLOB, GcsType.MODULE)

        val exportedPredicate : StreamContext.(XmlNodeModel) -> Boolean = {
            it.gtdNode?.intents?.any {
                it.gcsType in gcsTypes
            } ?: false
        }

        val noIntentOfDefaultPlane  : StreamContext.(XmlNodeModel) -> Boolean = {
            it.gtdNode?.intents?.none { it.tags.withInherited.contains(GTD_PLANE_DEFAULT) } ?: true
        }

        val predicate : StreamContext.(XmlNodeModel) -> Boolean = {
            noIntentOfDefaultPlane(it) && false || exportedPredicate(it)
        }

        root.stream()
            .apply { toSkip = true }
            .mark(mark, predicate)
    }

    operator fun invoke(root : XmlNodeModel) = calculate(root)
}