package org.freeplane.features.gtd.glob.exceptions

import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.glob.GlobModuleData
import org.freeplane.features.gtd.glob.scheme.ModulePlaceholder
import org.freeplane.features.map.MapModel
import org.freeplane.features.map.NodeModel

object OpenGtdGlobMapExceptionsHandler {
    fun handle(map : MapModel, modulesData: Map<String, GlobModuleData>,
               modulePlaceholders : Map<String, ModulePlaceholder>) {
        val exceptionsNode = NodeModel("exceptions", map)

        handleNotFoundModules(exceptionsNode, modulesData, modulePlaceholders)
        handleNotCountModules(exceptionsNode, modulesData, modulePlaceholders)

        exceptionsNode.takeIf { it.hasChildren() }?.let {
            it.side = NodeModel.Side.TOP_OR_LEFT
            GtdController.mapController.insertNode(it, map.rootNode)
        }
    }

    private fun handleNotFoundModules(
        exceptionsNode : NodeModel,
        modulesData: Map<String, GlobModuleData>,
        modulePlaceholders : Map<String, ModulePlaceholder>)
    {
        val notFoundModulesNodes = arrayListOf<NodeModel>()

        (modulePlaceholders.keys - modulesData.keys).forEach {
            notFoundModulesNodes.add(modulePlaceholders[it]!!.node)
        }

        insertNotFoundModulesNodes(exceptionsNode, notFoundModulesNodes)
    }

    private fun insertNotFoundModulesNodes(exceptionsNode : NodeModel, notFound : List<NodeModel>) {
        if(notFound.isEmpty()) return

        val map = exceptionsNode.map

        val notFoundParentNode = NodeModel("not found", map).also {
            exceptionsNode.insert(it)
        }

        notFound.forEach {
            var node = NodeModel(it.text, map)
            GtdController.setLink(node, GtdController.getLinkToNode(it))
            notFoundParentNode.insert(node)
        }
    }

    private fun handleNotCountModules(
        exceptionsNode : NodeModel,
        modulesData: Map<String, GlobModuleData>,
        modulePlaceholders : Map<String, ModulePlaceholder>)
    {
        val notCountModulesNodes = arrayListOf<NodeModel>()

        (modulesData.keys - modulePlaceholders.keys).forEach {
            notCountModulesNodes.add(modulesData[it]!!.node)
        }

        insertNotCountModulesNodes(exceptionsNode, notCountModulesNodes)
    }

    private fun insertNotCountModulesNodes(exceptionsNode : NodeModel, notCount : List<NodeModel>) {
        if(notCount.isEmpty()) return

        val map = exceptionsNode.map

        val notCountParentNode = NodeModel("not taken\ninto account", map).also {
            exceptionsNode.insert(it)
        }

        notCount.forEach {
            var node = NodeModel(it.text, map)
            GtdController.setLink(node, GtdController.getLinkToNode(it))
            notCountParentNode.insert(node)
        }
    }
}