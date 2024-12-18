package org.freeplane.features.custom.map.extensions

class WrapperExtension<T : Any>(val value : T, val key : Any = value::class) : KIExtension {
    override var spread : TreeSpread = TreeSpread.OWN
}