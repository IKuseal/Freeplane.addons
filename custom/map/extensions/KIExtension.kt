package org.freeplane.features.custom.map.extensions

import org.freeplane.core.extension.IExtension

interface KIExtension : IExtension {
    var spread : TreeSpread

    override fun setTreeSpread(spread: TreeSpread) {
        this.spread = spread
    }

    override fun getTreeSpread() = spread
}