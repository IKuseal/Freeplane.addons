package org.freeplane.features.custom.map.extensions

import org.freeplane.core.extension.IExtension
import org.freeplane.n3.nanoxml.XMLElement

object SpreadBuilder {
    const val ATTR = "spread"

    fun writeSpread(xmlElement : XMLElement, extension : IExtension) {
        xmlElement.setAttribute(ATTR, extension.treeSpread.name)
    }

    fun readSpread(value : String) = TreeSpread.valueOf(value)
}