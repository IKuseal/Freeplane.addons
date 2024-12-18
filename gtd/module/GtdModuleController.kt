package org.freeplane.features.gtd.module

import org.freeplane.core.undo.IActor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.map.GtdMap
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.module.actions.EnableLayer0ToExportAction
import org.freeplane.features.gtd.module.actions.EnableLayer1ToExportAction
import org.freeplane.features.gtd.module.actions.SwitchGtdModuleAction
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.forEach
import org.freeplane.features.map.NodeModel

object GtdModuleController {
    fun init() {
        GtdController.run {
            addAction(SwitchGtdModuleAction())
            addAction(EnableLayer0ToExportAction())
            addAction(EnableLayer1ToExportAction())
        }
    }

    fun switchGtdModule(gtdNode : GtdNodeModel, toCreate : Boolean) {
        if((gtdNode.module == null) != toCreate) return

        val newValue = if(toCreate) GtdModule() else null

        setGtdModule(gtdNode, newValue)
    }

    fun setGtdModule(gtdNode: GtdNodeModel, module : GtdModule?) {
        val oldValue = gtdNode.module
        val newValue = module?.also { it.setIdWith(gtdNode) }

        val actor = object : IActor {
            override fun act() {
                gtdNode.module = newValue
                GtdMapController.fireNodeChange(gtdNode)
            }

            override fun getDescription() = "setting Module to GtdNode"

            override fun undo() {
                gtdNode.module = oldValue
                GtdMapController.fireNodeChange(gtdNode)
            }
        }

        execute(actor, gtdNode)
    }

    fun enableLayer0ToExport(gtdNode : GtdNodeModel, module : GtdModule, enable : Boolean = true) {
        if(module.layer0ToExport == enable) return

        if(!enable) enableLayer1ToExport(gtdNode, module, false)

        val oldValue = module.layer0ToExport

        val actor = object : IActor {
            override fun act() {
                module.layer0ToExport = enable
                GtdMapController.fireNodeChange(gtdNode)
            }

            override fun getDescription() = "enable Module:layer0ToExport"

            override fun undo() {
                module.layer0ToExport = oldValue
                GtdMapController.fireNodeChange(gtdNode)
            }
        }

        execute(actor, gtdNode)
    }

    fun enableLayer1ToExport(gtdNode : GtdNodeModel, module : GtdModule, enable : Boolean = true) {
        if(module.layer1ToExport == enable) return

        if(enable) enableLayer0ToExport(gtdNode, module)

        val oldValue = module.layer1ToExport

        val actor = object : IActor {
            override fun act() {
                module.layer1ToExport = enable
                GtdMapController.fireNodeChange(gtdNode)
            }

            override fun getDescription() = "enable Module:layer1ToExport"

            override fun undo() {
                module.layer1ToExport = oldValue
                GtdMapController.fireNodeChange(gtdNode)
            }
        }

        execute(actor, gtdNode)
    }

    // access *********************************************************************************************
    fun findNodesWithModules(gtdMap : GtdMap, searchDepth : Int = Int.MAX_VALUE) : Collection<NodeModel> {
        val result = arrayListOf<NodeModel>()

        gtdMap.root.forEach(searchDepth) {
            if(module != null) result.add(node)
        }

        return result
    }

    // heap ***********************************************************************************************
    private fun execute(actor : IActor, gtdNode : GtdNodeModel) {
        GtdController.modeController.execute(actor, gtdNode.node.map)
    }

    val selectedGtdModuleSafe : GtdModule? get() = GtdMapController.selectedGtdNodeSafe?.module
}

const val MODULE_DEPTH_LIMIT = 5