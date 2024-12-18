package org.freeplane.features.gtd.map.cover

import org.freeplane.features.custom.createListComparator
import org.freeplane.features.custom.swap
import org.freeplane.features.gtd.node.GtdIntentCover
import org.freeplane.features.gtd.tag.GtdPlane
import org.freeplane.features.gtd.tag.GtdTag
import org.freeplane.features.gtd.tag.extWorkBuiltinTags
import org.freeplane.features.gtd.tag.planes
import java.util.*

class GtdTagsViewOrderAccessor(tags : List<GtdTag>) {
    val tags = tags.toMutableList()
    // tags order ******************************************************************
    private var orderedPlanes : List<GtdPlane> = arrayListOf()
    private var orderedOtherTags : List<GtdTag> = arrayListOf()

    fun swap(index1 : Int, index2 : Int) {
        tags.swap(index1, index2)
        isInvalidated = true
    }

    // validation **************
    private var isInvalidated = true

    private fun revalidate() {
        val list = tags.toMutableList()
        orderedPlanes = list.planes
        orderedOtherTags = list.apply { removeAll(orderedPlanes) }

        isInvalidated = false
    }

    // position ***************************************************************************************
    fun orderedIntents(intents : Collection<GtdIntentCover>) : List<GtdIntentCover> {
        val planesToOrderWith : GtdIntentCover.() -> Collection<GtdPlane> = {
            allPlanes.filter(orderedPlanes::contains)
        }

        val otherTagsToOrderWith : GtdIntentCover.() -> Collection<GtdTag> = {
            allOtherTags.filter(orderedOtherTags::contains)
        }

        val planeComparator = Comparator.comparingInt<GtdPlane> {
            orderedPlanes.indexOf(it).takeIf { it != -1 } ?: throw IllegalArgumentException()
        }

        val otherTagsComparator = Comparator.comparingInt<GtdTag> {
            orderedOtherTags.indexOf(it).takeIf { it != -1 } ?: throw IllegalArgumentException()
        }

        data class IntentData(
            val intent : GtdIntentCover,
            val planes : List<GtdPlane> = intent.planesToOrderWith().sortedWith(planeComparator),
            val otherTags : List<GtdTag> = intent.otherTagsToOrderWith().sortedWith(otherTagsComparator)
        )

        val comparator = Comparator
            .comparing(IntentData::planes, createListComparator(planeComparator))
            .thenComparing(IntentData::otherTags, createListComparator(otherTagsComparator))

        revalidate()

        return intents
            .map(::IntentData)
            .sortedWith(comparator)
            .map(IntentData::intent)
    }

    val topmostPlane : GtdPlane get() = orderedPlanes.first()
}

private val accessors : MutableMap<GtdMapCover, GtdTagsViewOrderAccessor> = WeakHashMap()

val GtdMapCover.tagsViewOrderAccessor 
    get() = accessors[this] 
        ?: GtdTagsViewOrderAccessor(extWorkBuiltinTags).also {
            accessors[this] = it
        }