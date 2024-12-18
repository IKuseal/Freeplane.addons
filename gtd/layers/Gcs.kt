package org.freeplane.features.gtd.layers

import org.freeplane.features.custom.map.isAttachedToMapTree
import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.gtd.conditions.base.GtdNodeCoverCondition
import org.freeplane.features.gtd.tag.GtdPlane
import org.freeplane.features.gtd.intent.GtdIntent
import org.freeplane.features.gtd.layers.GcsType.*
import org.freeplane.features.gtd.map.cover.GtdMapCover
import org.freeplane.features.gtd.node.*

class Gcs(val gcsData : GtdLayer, val root : GtdNodeCover) {
    private data class GcsWindow(var bottom: GcsType, var top: GcsType) {
        val isZeroSize get() = top < bottom

        fun shrink(other : GcsWindow) {
            if(isZeroSize || other.isZeroSize) return

            ensureBottomIsUpper(other.top)
        }

        private fun ensureBottomIsUpper(other : GcsType) {
            if(isZeroSize || other < bottom) return

            if(other == GLOB) {
                bottom = GLOB
                top = LOCAL
                return
            }

            bottom = other.upperType
        }

        fun isMatched(type: GcsType) =
            if(!isZeroSize) type in bottom..top
            else throw IllegalStateException()

        fun isMatched(intent : GtdNodeCover.GtdIntentCover) =
            isMatched(intent.gcsType)
    }

    private inner class GcsIncludedCondition : GtdNodeCoverCondition() {
        override fun checkGtdNodeCover(node: GtdNodeCover) =
            node.isIncluded

        override val label: String
            get() = "Gcs:$gcsType included"
    }

    val gcsType by gcsData::gcsType

    private var isInvalidated = false

    private val calcKey : Any get() = this

    private var GtdNodeCover.includedIntents : MutableList<GtdPlane>?
        get() = getPinnedData(calcKey)
        set(value) {
            setPinnedData(calcKey, value)
        }

    private fun GtdNodeCover.createIncludedIntents() =
        includedIntents ?: arrayListOf<GtdPlane>().also { includedIntents = it }

    fun includedIntentsOf(gtdNodeC : GtdNodeCover) = gtdNodeC.includedIntents

    private val GtdNodeCover.isIncluded
        get() = includedIntents != null

    fun isIncludedOf(gtdNodeC: GtdNodeCover) = gtdNodeC.isIncluded

    private val window : GcsWindow
        get() = when(gcsType) {
            GLOB -> GcsWindow(GLOB, GLOB)
            MODULE ->
                root.standardGcs?.let { GcsWindow(LOCAL, MODULE).apply { shrink(it.window) } }
                    ?: GcsWindow(LOCAL, MODULE)
            else -> GcsWindow(LOCAL, gcsType)
        }

    // calculation ******************************************************************************************
    fun calculate() {
        if(isInvalidated) throw IllegalStateException("Gcs is already invalidated")
        if(!isValid()) {
            invalidate()
            throw IllegalArgumentException("Gcs is invalid")
        }

        calculateInternal()
    }

    private fun calculateInternal() {
        resetInternal()

        fun calculateIntent(intent: GtdNodeCover.GtdIntentCover, gtdNodeC: GtdNodeCover, window: GcsWindow) : Boolean {
            if(window.isMatched(intent)) {
                gtdNodeC.createIncludedIntents().add(intent.plane)
                return true
            }
            return false
        }
        fun calculateNode(gtdNodeC : GtdNodeCover, window : GcsWindow) : Boolean {
            var isNodeIncluded = false
            if(!gtdNodeC.isFakeIntent) {
                gtdNodeC.intents.forEach {
                    if(calculateIntent(it, gtdNodeC, window))
                        isNodeIncluded = true
                }
            }
            else
                isNodeIncluded = calculateIntent(gtdNodeC.intentOrFake, gtdNodeC, window)

            return isNodeIncluded
        }
        fun updateWindow(window: GcsWindow, gtdNodeC: GtdNodeCover) {
            gtdNodeC.allGcs.forEach {
                window.shrink(it.window)
            }
        }

        val window = window

        root.children.forEach {
            it.forEachWithSpread<GcsWindow?>(window) {window ->
                var window = window?.copy() ?: return@forEachWithSpread null

                val isIncluded = calculateNode(this, window)

                if(isIncluded) window = this@Gcs.window

                updateWindow(window, this)

                window.takeIf { !it.isZeroSize }
            }
        }
    }

    private fun resetInternal() {
        root.gtdMapCRoot.forEach { includedIntents = null }
    }

    fun invalidate() {
        if(isInvalidated) return

        resetInternal()
        isInvalidated = true
    }

    fun isValid() = !isInvalidated
            && root.node.isAttachedToMapTree
            && (gcsData === root.layer
                || (gcsType == GLOB)
                || (gcsType == MODULE && root.gtdNodeModel.module != null))

    // condition ****************************************************************************************************
    val condition : ASelectableCondition
        get() = GcsIncludedCondition()
}

fun createGlobGcs(root: GtdNodeCover) = Gcs(GLOB_GCS, root)

fun createModuleGcs(root : GtdNodeCover) = Gcs(MODULE_GCS, root)

val GtdMapCover.globGcs get() = createGlobGcs(root)

val GtdNodeCover.globGcs get() =
    if(this === gtdMapCRoot) gtdMapCover.globGcs else null

val GtdNodeCover.moduleGcs get() =
    if(this === gtdMapCRoot || gtdNodeModel.module != null)
        createModuleGcs(this)
    else null

val GtdNodeCover.standardGcs get() =
    layer?.let { Gcs(it, this) }

val GtdNodeCover.allGcs get() = arrayListOf<Gcs>().also { list ->
    globGcs?.let { list.add(it) }
    moduleGcs?.let { list.add(it) }
    standardGcs?.let { list.add(it) }
}

val GtdNodeCover.matchedModuleGcs : Gcs get() {
    val root : GtdNodeCover =
        this.nextAncestor { it.gtdNodeModel.module != null }
            ?: gtdMapCover.root

    return createModuleGcs(root)
}

val GtdNodeCover.matchedStandardGcs : Gcs? get() =
    this.nextAncestor { it.layer != null }?.standardGcs

val GtdNodeCover.matchedGcs : Gcs get() =
    matchedStandardGcs ?: matchedModuleGcs

//fun GtdNodeCover.getGcs(gcsType : GcsType) = when(gcsType) {
//}