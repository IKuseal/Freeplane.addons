package org.freeplane.features.gtd.intent

import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.GtdStateClass.I
import org.freeplane.features.gtd.data.elements.GtdSubState
import org.freeplane.features.gtd.data.elements.GtdSubStateClass
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.tag.GtdPlane
import org.freeplane.features.gtd.tag.GtdTag
import java.util.*

open class GtdIntent (override var state : GtdState = I.default,
                      tags : Collection<GtdTag> = listOf(),
                      override var gcsType : GcsType = GcsType.LOCAL) : IGtdIntent {

    constructor(state : GtdState = I.default,
                plane : GtdPlane,
                gcsType : GcsType = GcsType.LOCAL
    ) : this(state, listOf(plane), gcsType)

    private var subStatesByClass: MutableMap<GtdSubStateClass, GtdSubState> =
        EnumMap(GtdSubStateClass::class.java)

    private val _tags : MutableSet<GtdTag> = tags.toMutableSet()

    // subStates **************************************************************************************
    override val subStatesMap: Map<GtdSubStateClass, GtdSubState>
        get() = subStatesByClass

    open fun putSubState(subState: GtdSubState) = subStatesByClass.put(subState.clazz, subState)

    open fun putAllSubStates(subStates: Collection<GtdSubState>) = subStates.forEach(::putSubState)

    open fun removeSubState(subStateClass: GtdSubStateClass) = subStatesByClass.remove(subStateClass)

    // tags **************************************************************************************
    override val tags: Set<GtdTag>
        get() = _tags

    open fun addTag(tag: GtdTag) {
        _tags.add(tag)
    }

    open fun addTags(tags : Collection<GtdTag>) {
        tags.forEach { addTag(it) }
    }

    open fun removeTag(tag: GtdTag) {
        _tags.remove(tag)
    }

    // * * *
    override fun copy() : GtdIntent = GtdIntent(state, tags, gcsType).also {
        it.subStatesByClass = EnumMap(subStatesByClass)
    }

    override fun equalsInternal(other: IGtdIntent) =
        this === other

    //    override val isEmpty get() = state == I.default && subStates.isEmpty()
}