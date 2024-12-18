package org.freeplane.features.gtd.layers

enum class GcsType {
    LOCAL, SUBDIRECTION, DIRECTION, MODULE, GLOB;

    val upperType : GcsType get() =
        if(ordinal == GcsType.values().size - 1) throw IllegalArgumentException()
        else GcsType.values()[ordinal+1]

    val lowerType : GcsType get() =
        if(ordinal == 0) throw IllegalArgumentException()
        else GcsType.values()[ordinal-1]
}

