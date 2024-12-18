package org.freeplane.features.custom.map.extensions

enum class TreeSpread {
    OWN { override val includesOwn = true },
    CHILD { override val includesOwn = false },
    DESCENDANT { override val includesOwn = false },
    TREE { override val includesOwn = true };

    abstract val includesOwn : Boolean
}