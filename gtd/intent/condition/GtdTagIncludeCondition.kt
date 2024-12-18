package org.freeplane.features.gtd.intent.condition

import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.tag.GtdTag

class GtdTagIncludeCondition(val tag : GtdTag, val inAllTags : Boolean = false) : GtdIntentCoverCondition() {
    override fun check(value: GtdNodeCover.GtdIntentCover) =
        if(inAllTags) value.containsInAllTags(tag) else value.containsTag(tag)
}