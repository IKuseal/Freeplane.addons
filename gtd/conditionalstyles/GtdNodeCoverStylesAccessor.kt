package org.freeplane.features.gtd.conditionalstyles

import org.freeplane.features.gtd.data.elements.GtdNum.*
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.GtdStateClass.R
import org.freeplane.features.gtd.layers.GcsType.*
import org.freeplane.features.gtd.layers.GtdLayer
import org.freeplane.features.gtd.layers.layer
import org.freeplane.features.gtd.module.GtdModule
import org.freeplane.features.gtd.node.GtdIntentCover
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.node.isDefault
import org.freeplane.features.gtd.tag.GTD_PLANE_MEMO
import org.freeplane.features.gtd.tag.GtdTag
import org.freeplane.features.styles.IStyle

class GtdNodeCoverStylesAccessor(gtdNodeCover : GtdNodeCover) {
    inner class GtdIntentCoverStylesAccessor(gtdIntentCover: GtdIntentCover, val isTopIntent : Boolean = false) {
        val gIntentC = gtdIntentCover

        val styles : Collection<IStyle> get() {
            val result : MutableCollection<IStyle> = linkedSetOf()
            val add : (IStyle) -> Unit = { result.add(it) }
            val addAll : (Collection<IStyle>) -> Unit = { result.addAll(it) }

            if(isTopIntent && gIntentC.containsTag(GTD_PLANE_MEMO) && gIntentC.state.clazz == R) {
                when(gIntentC.state.num) {
                    S0 -> STYLE_AI_0
                    S1 -> STYLE_AI_1
                    S2 -> STYLE_AI_2
                    S3 -> STYLE_AI_3
                    S4 -> STYLE_AI_4
                    S5 -> STYLE_TRANSPARENT
                    else -> throw IllegalStateException()
                }.let(add)

                return result
            }

            if(gIntentC.isDefault) {
                if(!isTopIntent || gMapC.useTotalCoverageModeDefaultStyle)
                    return result.also {
                        val defaultIntent = gIntentC.model as GtdNodeCover.GtdIntentDefault
                        defaultIntent.planeOfDefaultIntent.style?.let(add)
                        STYLE_GTD_INTENT_DEFAULT.let(add)
                    }
            }

            gIntentC.gcsType.style?.let(add)
            gIntentC.allTags.styles.let(addAll)
            gIntentC.subStatesMap.keys.forEach {
                it.style?.let(add)
            }
            gIntentC.state.styles.let(addAll)


            return result
        }

        private val GtdState.styles : Collection<IStyle> get() = arrayListOf<IStyle>().also {
            if(this == R(S5)) {
                STYLE_GTD_STATE_R5.let(it::add)
                return@also
            }
            clazz.style?.let(it::add)
            num.style?.let(it::add)
        }
    }

    val gNodeC = gtdNodeCover
    val gNodeM = gtdNodeCover.gtdNodeModel
    val gMapC = gtdNodeCover.gtdMapCover

    fun getConditionalStyles() : Collection<IStyle> {
        val result : MutableCollection<IStyle> = linkedSetOf()
        val add : (IStyle) -> Unit = { result.add(it) }
        val addAll : (Collection<IStyle>) -> Unit = { result.addAll(it) }

        gNodeC.isTransparent.transparencyStyle?.let(add)

        gNodeM.module?.style?.let(add)
        gNodeC.layer?.styles?.let(addAll)

        GtdIntentCoverStylesAccessor(gNodeC.intentOrFake, true).styles.let(addAll)

        return result
    }

    private val GtdModule.style get() = STYLE_GTD_MODULE

    private val GtdLayer.styles get() = arrayListOf<IStyle>().also {
        val levelStyle : Int.() -> IStyle = {
            if(this % 2 == 0) {
                when(gcsType) {
                    LOCAL -> STYLE_LAYER_LOCAL_0
                    SUBDIRECTION -> STYLE_LAYER_SUBDIRECTION_0
                    DIRECTION -> STYLE_LAYER_DIRECTION_0
                    else -> throw IllegalArgumentException()
                }
            }
            else when(gcsType) {
                LOCAL -> STYLE_LAYER_LOCAL_1
                SUBDIRECTION -> STYLE_LAYER_SUBDIRECTION_1
                DIRECTION -> STYLE_LAYER_DIRECTION_1
                else -> throw IllegalArgumentException()
            }
        }

        if(gMapC.doShowLayers) it.add(level.levelStyle())
        else it.add(STYLE_LAYER)
    }

    private val Collection<GtdTag>.styles : Collection<IStyle> get() {
        return mapNotNull(GtdTag::style)
    }

    private val Boolean.transparencyStyle : IStyle? get() = when(this) {
        true -> STYLE_TRANSPARENT
        false -> null
    }
}