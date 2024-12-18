package org.freeplane.features.logistics.search

import org.freeplane.features.logistics.destination.Destination

data class DestinationSearchElement(val destination : Destination, val index : String) {
    override fun toString() = index
}