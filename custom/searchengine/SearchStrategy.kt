package org.freeplane.features.custom.searchengine

abstract class SearchStrategy(val pattern : String) {
    abstract fun test(index : String) : Boolean
}