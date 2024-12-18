package org.freeplane.features.gtd.map.event

import org.freeplane.features.gtd.core.GtdEventProp
import org.freeplane.features.gtd.map.GtdMapController
import org.freeplane.features.gtd.node.GtdNodeModel

data class GtdNodeChangeEvent(val node : GtdNodeModel,
                              val prop : GtdEventProp = GtdMapController.gtdNodeEventUndefinedProp)