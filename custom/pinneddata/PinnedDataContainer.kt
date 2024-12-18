package org.freeplane.features.custom.pinneddata

class PinnedDataContainer : IPinnedDataContainer {
    private val container = hashMapOf<Any, Any>()

    override fun setPinnedData(key: Any, data: Any?) {
        data?.let { container[key] = data; data } ?: container.remove(key)
    }

    override fun <T : Any> getPinnedData(key: Any) = container[key] as T?
}