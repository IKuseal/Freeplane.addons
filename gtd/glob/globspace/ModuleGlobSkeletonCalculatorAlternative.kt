package org.freeplane.features.gtd.glob.globspace

import org.freeplane.features.custom.preprocess.XmlNodeModel
import org.freeplane.features.custom.preprocess.stream
import org.freeplane.features.custom.treestream.*
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.preprocess.gtdNode
import org.freeplane.features.gtd.tag.GTD_PLANE_DEFAULT
import org.freeplane.features.gtd.tag.withInherited

object ModuleGlobSkeletonCalculatorAlternative {
    fun calculate(root : XmlNodeModel) {
        val mark = root.generateMark()

        root.addMark(mark)
        markGlobExported(root, mark)
        markModuleExported(root, mark)
        markInitialExported(root, mark)

        root.stream().fillMark(mark)

        root.stream().cutNotMark(mark)
    }

    private fun markGlobExported(root : XmlNodeModel, mark : String) {
        val exportedPredicate : StreamContext.(XmlNodeModel) -> Boolean = {
            it.gtdNode?.intents?.any {
                it.gcsType == GcsType.GLOB
            } ?: false
        }

        val noIntentOfDefaultPlane  : StreamContext.(XmlNodeModel) -> Boolean = {
            it.gtdNode?.intents?.none { it.tags.withInherited.contains(GTD_PLANE_DEFAULT) } ?: true
        }

        val predicate : StreamContext.(XmlNodeModel) -> Boolean = {
            noIntentOfDefaultPlane(it)
                    ||
                    exportedPredicate(it)
        }

        root.stream()
            .apply { toSkip = true } // root of a module not in account
            .mark(mark, predicate)
    }

    private fun markModuleExported(root : XmlNodeModel, mark : String) {
        val exportedPredicate : StreamContext.(XmlNodeModel) -> Boolean = {
            it.gtdNode?.intents?.any {
                it.gcsType == GcsType.MODULE
            } ?: false
        }

        root.stream()
            .apply { toSkip = true } // root of a module not in account
            .mark(mark, exportedPredicate)
    }

    private fun markInitialExported(root : XmlNodeModel, mark : String) {
        root.stream()
            .mark(mark) {
                if(it.gtdNode?.layer != null) {
                    isStoppedChildren = true
                }

                true
            }
    }

    operator fun invoke(root : XmlNodeModel) = calculate(root)
}