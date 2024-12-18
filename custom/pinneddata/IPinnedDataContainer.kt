package org.freeplane.features.custom.pinneddata

interface IPinnedDataContainer {
    fun setPinnedData(key : Any, data : Any?)

    fun <T : Any> getPinnedData(key : Any) : T?
}