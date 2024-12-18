package org.freeplane.features.logistics.addressing.scope

import org.freeplane.features.custom.map.extensions.KIExtension
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.custom.map.getExt
import org.freeplane.features.custom.map.putExt
import org.freeplane.features.logistics.addressing.NodeModelTextTransformer
import org.freeplane.features.map.NodeModel

class Scope(val node : NodeModel) : KIExtension {
    override var spread : TreeSpread = TreeSpread.OWN

    val name : String
        get() = nameStrategy()

    var nameStrategy : () -> String = NodeModelTextTransformer(node)::transform

    val path : List<Scope> get() {
        val path = arrayListOf<Scope>()
        var parent = node.parentNode
        while(parent != null) {
            parent.logisticsScope?.let { path.add(it) }
            parent = parent.parentNode
        }
        return path.reversed()
    }
}

var NodeModel.logisticsScope : Scope?
    get() = getExt(Scope::class)
    set(value) {
        putExt(Scope::class, value)
    }

fun NodeModel.createLogisticsScope() =
    logisticsScope ?: Scope(this).also { logisticsScope = it }