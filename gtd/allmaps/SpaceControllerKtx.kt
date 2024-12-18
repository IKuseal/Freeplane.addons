package org.freeplane.features.gtd.allmaps

import org.freeplane.features.custom.preprocess.XmlMapModel
import org.freeplane.features.custom.space.MapFile
import org.freeplane.features.custom.space.SpaceController

val MapFile.isGtdInstalled
    get() = XmlMapModel.create(this).gtdConfiguration != null

val MapFile.isAttachedToGtdGlob
    get() = XmlMapModel.create(this).gtdConfiguration?.attachedToGlobal ?: false

val SpaceController.gtdInstalledMaps
    get() = maps.filter { it.isGtdInstalled }

val SpaceController.attachedToGtdGlobMaps
    get() = maps.filter { it.isAttachedToGtdGlob }