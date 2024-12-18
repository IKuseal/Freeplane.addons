package org.freeplane.features.gtd.layers

import org.freeplane.core.undo.IActor
import org.freeplane.features.custom.map.extensions.SpreadsMap
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.custom.map.extensions.TreeSpread.CHILD
import org.freeplane.features.custom.map.extensions.TreeSpread.OWN
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.layers.GtdLayerType.*
import org.freeplane.features.gtd.layers.actions.SetGtdLayerTypeMenuBuilder.SET_GTD_LAYER_TYPE_MENU_BUILDER
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.map.cover.GtdMapCover
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.map.cover.event.IGtdMapCoverChangeListener
import org.freeplane.features.custom.workspread.WorkSpreadController
import org.freeplane.features.gtd.layers.actions.*
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.forEach
import org.freeplane.features.gtd.node.forEachWithSpread
import org.freeplane.features.map.NodeModel

object GtdLayerController : IGtdMapCoverChangeListener {
    val gtdMapCoverUpdatedLayersEventProp = GtdEventProp(GtdMapCoverController.gtdMapCoverUpdatedEventProp)
    private val validSpreads = arrayOf(OWN, CHILD)

    fun init() {
        GtdController.run {
            addUiBuilder(SET_GTD_LAYER_TYPE_MENU_BUILDER, SetGtdLayerTypeMenuBuilder)
            addUiBuilder(SetGcsTypeMenuBuilder.key, SetGcsTypeMenuBuilder)
            addAction(RemoveGtdLayerAction())
            addAction(SwitchGtdLayerExcludeFlagAction())
            addAction(EnableDoShowGtdLayersAction())
        }
    }

    private val TreeSpread.isValid get() = this in validSpreads

    val activeSpread get() = WorkSpreadController.workSpread

    fun setSpreadsMap(gtdNode: GtdNodeModel, spreadsMap: SpreadsMap<GtdLayer>) {
        val oldSpreadMap = gtdNode.layerSpreadsMap

        val actor = object : IActor {
            override fun act() {
                gtdNode.layerSpreadsMap = spreadsMap
                GtdMapController.fireNodeChange(gtdNode, GtdMapController.gtdNodeEventSpreadProp)
            }

            override fun getDescription() = "setting LayerSpreadsMap"

            override fun undo() {
                gtdNode.layerSpreadsMap = oldSpreadMap
                GtdMapController.fireNodeChange(gtdNode, GtdMapController.gtdNodeEventSpreadProp)
            }
        }

        execute(actor, gtdNode)
    }

    fun setLayer(gtdNode: GtdNodeModel, layer : GtdLayer?, spread : TreeSpread = activeSpread) {
        if(!spread.isValid) return

        val spreadsMap = gtdNode.layerSpreadsMap.apply { this[spread] = layer }
        setSpreadsMap(gtdNode, spreadsMap)
    }

    fun switchLayer(gtdNode: GtdNodeModel, toOn : Boolean, spread: TreeSpread = activeSpread) {
        if(!spread.isValid) return
        if(gtdNode.containsLayer(spread) == toOn) return

        val layer = if(toOn) GtdLayer() else null

        setLayer(gtdNode, layer, spread)
    }

    fun createLayer(gtdNode: GtdNodeModel, spread: TreeSpread = activeSpread) {
        switchLayer(gtdNode, true, spread)
    }

    fun removeLayer(gtdNode: GtdNodeModel, spread: TreeSpread = activeSpread) {
        switchLayer(gtdNode, false, spread)
    }

    fun setLayerType(gtdNode: GtdNodeModel, type: GtdLayerType, spread: TreeSpread = activeSpread) {
        val layer = gtdNode.getLayer(spread)?.takeIf { it.type != type} ?: return

        val oldType = layer.type

        val actor = object : IActor {
            override fun act() {
                layer.type = type
                GtdMapController.fireNodeChange(gtdNode)
            }

            override fun getDescription() = "setting GtdLayerType"

            override fun undo() {
                layer.type = oldType
                GtdMapController.fireNodeChange(gtdNode)
            }
        }

        execute(actor, gtdNode)
    }

    fun setLayerGcsType(gtdNode: GtdNodeModel, gcsType: GcsType, spread: TreeSpread = activeSpread) {
        val layer = gtdNode.getLayer(spread)?.takeIf { it.gcsType != gcsType} ?: return

        val oldGcsType = layer.gcsType

        val actor = object : IActor {
            override fun act() {
                layer.gcsType = gcsType
                GtdMapController.fireNodeChange(gtdNode)
            }

            override fun getDescription() = "setting GtdLayerGcsType"

            override fun undo() {
                layer.gcsType = oldGcsType
                GtdMapController.fireNodeChange(gtdNode)
            }
        }

        execute(actor, gtdNode)
    }

    fun resetLayerSpreads(gtdNode: GtdNodeModel) {
        setSpreadsMap(gtdNode, emptyLayerSpreadsMap)
    }

    // exclude flag ****************************************************************************************
    fun switchExcludeFlag(gtdNode: GtdNodeModel, toEnable : Boolean) {
        if(gtdNode.layerExcludeFlag == toEnable) return

        val oldValue = gtdNode.layerExcludeFlag

        val actor = object : IActor {
            override fun act() {
                gtdNode.layerExcludeFlag = toEnable
                GtdMapController.fireNodeChange(gtdNode, GtdMapController.gtdNodeEventSpreadProp)
            }

            override fun getDescription() = "setting GtdLayer ExcludeFlag"

            override fun undo() {
                gtdNode.layerExcludeFlag = oldValue
                GtdMapController.fireNodeChange(gtdNode, GtdMapController.gtdNodeEventSpreadProp)
            }
        }

        execute(actor, gtdNode)
    }

    // changes and recalculation ***************************************************************************
    override fun onGtdMapCoverChange(map: GtdMapCover, prop: GtdEventProp) {
        if(prop.iz(GtdMapCoverController.gtdMapCoverUpdatedMainEventProp)) updateLayers(map)
    }

    fun updateLayers(gtdMapC : GtdMapCover) {
        calculateLayers(gtdMapC)
        GtdMapCoverController.fireGtdMapCoverChange(gtdMapC, gtdMapCoverUpdatedLayersEventProp)
    }

    data class CalcData(val parentLevel : Int = -1, val localLayerLevel : Int = -1, )

    private fun calculateLayers(gtdMapC: GtdMapCover) {
        val root = gtdMapC.root

        root.forEachWithSpread(CalcData()) { calcData ->
            var calcData = calcData

            if(calcData.localLayerLevel == -1) gtdNodeModel.module?.let {
                calcData = calcData.copy(localLayerLevel = it.localLayerLevel) }

            val layer = this.layer ?: return@forEachWithSpread calcData

            var (parentLevel, localLayerLevel) = calcData

            val tempLocalLayerLevel = if(localLayerLevel != -1) localLayerLevel else 0

            val level = levelForLayerType(layer.type, parentLevel, tempLocalLayerLevel)

            layer.level = level

            CalcData(level, localLayerLevel)
        }
    }

    private fun levelForLayerType(type : GtdLayerType, parentLevel: Int, localLayerLevel: Int) = when(type) {
        LayerLocal -> if(parentLevel + 1 >= localLayerLevel) parentLevel + 1 else localLayerLevel
        Layer0 -> parentLevel + 1
        Layer1 -> if(parentLevel + 1 >= 1) parentLevel + 1 else 1
    }

    fun enableDoShowLayers(gtdMapC : GtdMapCover, enable : Boolean = true) {
        if(gtdMapC.doShowLayers == enable) return

        gtdMapC.doShowLayers = enable

        updateLayers(gtdMapC)
    }

    // access *********************************************************************************************
    fun getNodesWithLayers(gtdMapC : GtdMapCover, searchDepth : Int = Int.MAX_VALUE) : Collection<NodeModel> =
        getNodesWithLayers(gtdMapC.root)

    fun getNodesWithLayers(root : GtdNodeCover, searchDepth : Int = Int.MAX_VALUE) : Collection<NodeModel> =
        arrayListOf<NodeModel>().apply {
            root.forEach(searchDepth) {
                if(layer != null) add(node)
            }
        }

        // heap ***********************************************************************************************
    private fun execute(actor : IActor, gtdNode : GtdNodeModel) {
        GtdController.modeController.execute(actor, gtdNode.node.map)
    }
}

const val MAX_LAYER_LEVEL = 5