package org.freeplane.features.gtd.intent

import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.GtdSubState
import org.freeplane.features.gtd.data.elements.GtdSubStateClass
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.tag.*

interface IGtdIntent {
    val state : GtdState
    val gcsType : GcsType

    // subStates ************************************************************************************
    val subStatesMap : Map<GtdSubStateClass, GtdSubState>
    val subStates: Collection<GtdSubState> get() = subStatesMap.values
    fun getSubState(subStateClass: GtdSubStateClass) = subStatesMap[subStateClass]
    fun containsSubState(subStateClass: GtdSubStateClass) = subStatesMap.contains(subStateClass)

    // tags ************************************************************************************
    val tags: Set<GtdTag>

    val allTags: Set<GtdTag> get() = hashSetOf<GtdTag>().apply {
        tags.forEach {
            add(it)
            addAll(it.allInheritedTags)
        }
    }

    fun containsTag(tag: GtdTag) = tags.contains(tag)

    fun containsInAllTags(tag: GtdTag) = allTags.contains(tag)

    val plane: GtdPlane
        get() = tags.first { it is GtdPlane } as GtdPlane

    val workPlane: GtdPlane?
        get() = tags.planes.firstOrNull { workBuiltinPlanes.contains(it) }

    val planes : Collection<GtdPlane> get() = tags.planes
    val allPlanes : Collection<GtdPlane> get() = allTags.planes

    val spaces : Collection<GtdSpace> get() = tags.spaces
    val allSpaces : Collection<GtdSpace> get() = allTags.spaces

    val otherTags : Collection<GtdTag> get() = tags.otherTags
    val allOtherTags : Collection<GtdTag> get() = allTags.otherTags

    // * * *
    fun copy(): IGtdIntent

    fun equalsInternal(other : IGtdIntent) : Boolean
}