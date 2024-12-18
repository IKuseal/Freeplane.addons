package org.freeplane.features.gtd.map.event

import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.map.GtdMap
import org.freeplane.features.gtd.map.GtdMapController

data class GtdMapChangeEvent(val map : GtdMap,
                             val prop : GtdEventProp = GtdMapController.gtdMapEventUndefinedProp)