package org.freeplane.features.gtd.node

import org.freeplane.core.extension.IExtension
import org.freeplane.features.custom.map.*
import org.freeplane.features.custom.map.extensions.SpreadsMap
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.custom.map.extensions.WrapperSpreadsMap
import org.freeplane.features.custom.treestream.forEach
import org.freeplane.features.custom.treestream.forEachWithSpread
import org.freeplane.features.custom.treestream.nextAncestor
import org.freeplane.features.gtd.data.elements.*
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.layers.deepCopy
import org.freeplane.features.gtd.layers.layerExcludeFlag
import org.freeplane.features.gtd.layers.layerSpreadsMap
import org.freeplane.features.gtd.map.gtdMap
import org.freeplane.features.gtd.module.GtdModule
import org.freeplane.features.map.NodeModel
import java.util.*
import kotlin.reflect.KClass

// TODO(RVC)
open class GtdNodeModel(override val node : NodeModel) : GtdNode() {
    val gtdMap get() = node.map.gtdMap

    private val _intents : MutableSet<GtdIntent> = hashSetOf()

    override val intents : Collection<GtdIntent> get() = _intents.toList()

    // tree ************************************************************************************************
    val parent : GtdNodeModel? get() = node.parentNode?.gtdNode

    val children : Collection<GtdNodeModel> get() = node.children.map { it.gtdNode }

    // intents ****************************************************************************************

    fun addIntent(intent: GtdIntent) {
        _intents.add(intent)
    }

    fun removeIntent(intent: GtdIntent) {
        _intents.remove(intent)
        onIntentRemove(intent)
    }

    fun containsIntent(intent: GtdIntent) = _intents.contains(intent)

    // original version (at least because of GtdIntentCover with equality to...)
    fun getIntent(intent: GtdIntent) : GtdIntent? {
        _intents.forEach { if(it == intent) return it }
        return null
    }

    // intent set listeners ************
    private val intentSetListeners : MutableSet<IGtdIntentSetListener> =
        Collections.newSetFromMap(WeakHashMap())

    fun addIntentSetListener(l : IGtdIntentSetListener) {
        intentSetListeners.add(l)
    }

    private fun onIntentRemove(intent: GtdIntent) {
        intentSetListeners.forEach {
            it.onIntentRemove(intent)
        }
    }

    // spread states ******************************************************************************
    fun <T : GtdStatus> getStatusSpreadsMap(spreadsClazz : KClass<out IExtension>) : WrapperSpreadsMap<T> {
        return node.getWrapperSpreadsMap(spreadsClazz)
    }

    fun setStatusSpreadsMap(spreadsMap : SpreadsMap<*>) = node.putSpreadsMap(spreadsMap)

    fun getStateSpreadsMap() : WrapperSpreadsMap<GtdState> = getStatusSpreadsMap(GtdStateClass.I.spreadClass)

    fun setStateSpreadsMap(spreadsMap : WrapperSpreadsMap<*>) = setStatusSpreadsMap(spreadsMap)

    fun getSubStateSpreadsMap(subStateClass: GtdSubStateClass) : WrapperSpreadsMap<GtdSubState> =
        getStatusSpreadsMap(subStateClass.spreadClass)

    fun setSubStateSpreadsMap(spreadsMap : WrapperSpreadsMap<*>) = setStatusSpreadsMap(spreadsMap)

    fun setSpreadState(state: GtdState, spread: TreeSpread) {
        getStateSpreadsMap().run {
            setValue(spread, state)
            setStateSpreadsMap(this)
        }
    }

    fun setSpreadSubState(subState: GtdSubState, spread: TreeSpread) {
        getSubStateSpreadsMap(subState.clazz).run {
            setValue(spread, subState)
            setSubStateSpreadsMap(this)
        }
    }

    // copy ****************************************************************************************************
    override fun copy(): GtdNodeModel = copy(::copyIntents)

    fun copy(copyIntents : (GtdNodeModel) -> Unit) : GtdNodeModel {
        val node = FakeMapModel.newNode()
        val gtdNode = node.gtdNode

        copyIntents(gtdNode)

        gtdNode.module = module?.copy()

        gtdNode.layerSpreadsMap = layerSpreadsMap.deepCopy()
        if(layerExcludeFlag) gtdNode.layerExcludeFlag = true

        return gtdNode
    }

    private fun copyIntents(node : GtdNodeModel) {
        intents.forEach {
            node.addIntent(it.copy())
        }
    }

    private fun copySpreadStatuses(node : GtdNodeModel) {
        node.setStateSpreadsMap(getStateSpreadsMap())
        GtdSubStateClass.values().forEach {
            node.setSubStateSpreadsMap(getSubStateSpreadsMap(it))
        }
    }

    // extensions **********************************************************************************************
    var module : GtdModule? = null

    // heap *****************************************************************************************************
    val isEmpty get() =
        intents.isEmpty()
                && getStateSpreadsMap().isEmpty
                && GtdSubStateClass.values().all {
                    getSubStateSpreadsMap(it).isEmpty
                }
                && module == null
                && layerSpreadsMap.isEmpty
                && !layerExcludeFlag
}

val NodeModel.gtdNode
    get() = getExt(GtdNodeModel::class)  ?: GtdNodeModel(this).also { putExt(it) }

// convenience functions **********************************************************************************************
fun GtdNodeModel.getSpreadState(spread: TreeSpread) = getStateSpreadsMap().getValue(spread)

// spreads map ********************************************************************************************************
fun emptyStateSpreadsMap() = WrapperSpreadsMap<GtdState>(GtdStateClass.I.spreadClass)

fun emptySubStateSpreadsMap(clazz : GtdSubStateClass) = WrapperSpreadsMap<GtdSubState>(clazz.spreadClass)

// copy, reset, isEmpty

// tree stream ***********************************************************************************************
fun GtdNodeModel.forEach(depth : Int = Int.MAX_VALUE, action : GtdNodeModel.() -> Unit) {
    this.forEach(GtdNodeModel::children, depth, action)
}

fun <T> GtdNodeModel.forEachWithSpread(initial : T, depth : Int = Int.MAX_VALUE, action : GtdNodeModel.(T) -> T) {
    this.forEachWithSpread(GtdNodeModel::children, initial, depth, action)
}

fun GtdNodeModel.nextAncestor(condition : (GtdNodeModel) -> Boolean) : GtdNodeModel? {
    return this.nextAncestor(GtdNodeModel::parent, condition)
}

// relations ***********************************************************************************************
val GtdNodeModel.branchRoot get() = nextAncestor { it.parent == null } ?: this

