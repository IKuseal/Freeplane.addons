package org.freeplane.features.logistics.delivery

class DeliveryExtras {
    interface Key

    private val storage = hashMapOf<Key, Any>()

    operator
    fun set(key : Key, value : Any) {
        storage[key] = value
    }

    operator fun get(key : Key) = storage[key]
}