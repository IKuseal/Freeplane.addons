package org.freeplane.features.gtd.intent.condition

import org.freeplane.features.gtd.conditions.base.GtdTopIntentCondition
import org.freeplane.features.gtd.node.GtdIntentCover
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.tag.GtdTag

class GtdNodeCTagIncludeCondition(tag : GtdTag) : GtdTopIntentCondition() {
    private val condition = GtdTagIncludeCondition(tag)
    override val label get() = condition.tag.name

    override fun checkIntent(gNodeC: GtdNodeCover, intent: GtdIntentCover) =
        condition.check(intent)
}