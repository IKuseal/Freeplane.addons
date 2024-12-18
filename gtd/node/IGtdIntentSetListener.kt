package org.freeplane.features.gtd.node

import org.freeplane.features.gtd.intent.GtdIntent

interface IGtdIntentSetListener {
    fun onIntentRemove(intent: GtdIntent)
}