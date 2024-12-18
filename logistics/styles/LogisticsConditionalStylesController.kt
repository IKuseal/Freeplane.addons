package org.freeplane.features.logistics.styles

import org.freeplane.features.logistics.addressing.address.logisticsAddress
import org.freeplane.features.logistics.addressing.scope.logisticsScope
import org.freeplane.features.map.NodeModel
import org.freeplane.features.styles.IStyle

object LogisticsConditionalStylesController {
    fun getStyles(node : NodeModel) : Collection<IStyle> {
        val result : MutableCollection<IStyle> = linkedSetOf()

        if(node.logisticsScope != null) result.add(STYLE_SCOPE)
        if(node.logisticsAddress != null) result.add(STYLE_ADDRESS)

        return result
    }
}