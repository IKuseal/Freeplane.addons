package org.freeplane.features.gtd.configuration

import org.freeplane.core.extension.IExtension
import org.freeplane.features.map.MapModel

class GtdConfiguration() : IExtension {
    var gtdEnabled : Boolean = true
    var attachedToGlobal : Boolean = true
}


var MapModel.gtdConfiguration : GtdConfiguration?
    get() = getExtension(GtdConfiguration::class.java)
    set(value) {
        putExtension<GtdConfiguration>(GtdConfiguration::class.java, value)
    }

fun MapModel.createGtdConfiguration() : GtdConfiguration {
    return gtdConfiguration ?: GtdConfiguration().apply { gtdConfiguration = this }
}

val MapModel?.isGtdActive
    get() = if(this == null) false else gtdConfiguration?.gtdEnabled ?: false

val MapModel?.isGtdInstalled
    get() = if(this == null) false else gtdConfiguration != null