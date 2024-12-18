package org.freeplane.features.gtd.intentsfiltering

import org.freeplane.features.custom.condition.Condition
import org.freeplane.features.custom.condition.conjunctCondition
import org.freeplane.features.custom.condition.disjunctCondition
import org.freeplane.features.custom.condition.not
import org.freeplane.features.gtd.conditions.base.ContainsRequirement
import org.freeplane.features.gtd.conditions.base.ContainsRequirement.*
import org.freeplane.features.gtd.intent.condition.GtdTagIncludeCondition
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.tag.*

class IntentsFilteringConditionModel() {
    val planesModel : MutableMap<GtdPlane, ContainsRequirement> = linkedMapOf<GtdPlane, ContainsRequirement>().apply {
        extWorkBuiltinPlanes.forEach {
            this[it] = NO_MATTER
        }
    }

    val spacesModel : MutableMap<GtdSpace, ContainsRequirement> = linkedMapOf<GtdSpace, ContainsRequirement>().apply {
        extWorkBuiltinSpaces.forEach {
            this[it] = NO_MATTER
        }
    }

    val otherTagsModel : MutableMap<GtdTag, ContainsRequirement> = linkedMapOf<GtdTag, ContainsRequirement>().apply {
        extWorkBuiltinOtherTags.forEach {
            this[it] = NO_MATTER
        }
    }

    val otherTagsModelConjunctInclude : MutableMap<GtdTag, ContainsRequirement> = linkedMapOf<GtdTag, ContainsRequirement>().apply {
        extWorkBuiltinOtherTags.forEach {
            this[it] = NO_MATTER
        }
    }

    private val <K : GtdTag> Map<K, ContainsRequirement>.tagsToInclude : Collection<GtdTag>
        get() = filterValues { it == INCLUDE }.keys

    private val <K : GtdTag> Map<K, ContainsRequirement>.tagsToExclude : Collection<GtdTag>
        get() = filterValues { it == EXCLUDE }.keys

    var condition : Condition<GtdNodeCover.GtdIntentCover>? = createCondition()
    private set

    private fun createCondition() : Condition<GtdNodeCover.GtdIntentCover>? {
        fun createIncludeCondition(tags : Collection<GtdTag>) =
            tags.map(::GtdTagIncludeCondition)
                .takeIf { it.isNotEmpty() }
                ?.disjunctCondition
        fun createConjunctIncludeCondition(tags : Collection<GtdTag>) =
            tags.map(::GtdTagIncludeCondition)
                .takeIf { it.isNotEmpty() }
                ?.conjunctCondition
        fun createExcludeCondition(tags : Collection<GtdTag>) =
            tags.map(::GtdTagIncludeCondition)
                .map { it.not }
                .takeIf { it.isNotEmpty() }
                ?.conjunctCondition

        println(planesModel)

        val conditions : MutableList<Condition<GtdNodeCover.GtdIntentCover>> = arrayListOf()

        createIncludeCondition(spacesModel.tagsToInclude)?.let { conditions.add(it) }
        createExcludeCondition(spacesModel.tagsToExclude)?.let { conditions.add(it) }

        createIncludeCondition(planesModel.tagsToInclude)?.let { conditions.add(it) }
        createExcludeCondition(planesModel.tagsToExclude)?.let { conditions.add(it) }

        createIncludeCondition(otherTagsModel.tagsToInclude)?.let { conditions.add(it) }
        createExcludeCondition(otherTagsModel.tagsToExclude)?.let { conditions.add(it) }

        createConjunctIncludeCondition(otherTagsModelConjunctInclude.tagsToInclude)?.let { conditions.add(it) }

        return conditions.takeIf { it.isNotEmpty() }?.conjunctCondition
    }

    fun revalidate() {
        condition = createCondition()
    }

    fun check(intentC : GtdNodeCover.GtdIntentCover) =
        condition?.check(intentC) ?: true
}