package org.freeplane.features.logistics.addressing.address

import org.freeplane.features.custom.map.extensions.KIExtension
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.custom.map.getExt
import org.freeplane.features.custom.map.putExt
import org.freeplane.features.logistics.addressing.NodeModelTextTransformer
import org.freeplane.features.logistics.addressing.scope.Scope
import org.freeplane.features.map.NodeModel

class Address(val node : NodeModel) : KIExtension {
    override var spread : TreeSpread = TreeSpread.OWN

    val name : String
        get() = nameStrategy()

    var nameStrategy : () -> String = NodeModelTextTransformer(node)::transform

    var path : List<Scope> = arrayListOf()
}

var NodeModel.logisticsAddress : Address?
    get() = getExt(Address::class)
    set(value) {
        putExt(Address::class, value)
    }

fun NodeModel.createLogisticsAddress() =
    logisticsAddress ?: Address(this).also { logisticsAddress = it }

val List<Scope>.asPathString get() = joinToString("/") { it.name }

