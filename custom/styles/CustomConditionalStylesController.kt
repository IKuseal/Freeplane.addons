package org.freeplane.features.logistics.styles

import org.freeplane.features.custom.styles.STYLE_SYNC_NODE
import org.freeplane.features.custom.styles.STYLE_TESTING_MARK_1
import org.freeplane.features.custom.synchronizednodes.hasSyncNodeExtension
import org.freeplane.features.custom.test.TestingMarkType
import org.freeplane.features.custom.test.testingMarkExtension
import org.freeplane.features.map.NodeModel
import org.freeplane.features.styles.IStyle

object CustomConditionalStylesController {
    fun getStyles(node : NodeModel) : Collection<IStyle> {
        val result : MutableCollection<IStyle> = linkedSetOf()

        if(node.hasSyncNodeExtension()) result.add(STYLE_SYNC_NODE)
        testingMarkExtensionStyle(node)?.let { result.add(it) }

        return result
    }
    
    private
    fun testingMarkExtensionStyle(node : NodeModel) : IStyle? =
        when(node.testingMarkExtension?.type) {
            TestingMarkType.ONE -> STYLE_TESTING_MARK_1
            else -> null
        }
}