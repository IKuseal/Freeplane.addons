package org.freeplane.features.gtd.map.cover

import org.freeplane.features.gtd.tag.GTD_PLANE_DEFAULT
import org.freeplane.features.gtd.tag.GtdPlane
import org.freeplane.features.gtd.tag.GtdTag
import org.freeplane.features.gtd.tag.planes

class GtdTagCreationParamsAccessor(tags : Set<GtdTag>) {
    private val tagsByEnabled = mutableMapOf<GtdTag, Boolean>().apply {
        tags.forEach { put(it, false) }
        this[GTD_PLANE_DEFAULT] = true
    }

    var singlePlaneMode = true
        set(value) {
            println("singPlaneMode1: $singlePlaneMode")
            field = value
            println("singPlaneMode2: $singlePlaneMode")
            if(value) setTagEnabled(GTD_PLANE_DEFAULT)
        }

    fun isTagEnabled(tag : GtdTag) = tagsByEnabled[tag]!!

    fun setTagEnabled(tag : GtdTag, enabled : Boolean = true) {
        if(singlePlaneMode && enabled && tag is GtdPlane) {
            tagCreationParams.planes.forEach {
                tagsByEnabled[it] = false
            }
        }
        tagsByEnabled[tag] = enabled
    }

    fun switchTag(tag : GtdTag) {
        setTagEnabled(tag, !tagsByEnabled[tag]!!)
    }

    val tagCreationParams get() = tagsByEnabled.filter { (_,enabled) -> enabled }.keys
}