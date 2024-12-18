package org.freeplane.features.gtd.tag

class GtdSpace(name : String, inheritedTags : Set<GtdTag> = hashSetOf()) : GtdTag(name, inheritedTags) {
    override fun toString() = name
}

val workBuiltinSpaces : List<GtdSpace> = workBuiltinTags.spaces

val workBuiltinSpacesByName : Map<String, GtdSpace> = linkedMapOf<String, GtdSpace>().apply {
    workBuiltinSpaces.forEach {
        this[it.name] = it
    }
}

val extWorkBuiltinSpaces : List<GtdSpace> = extWorkBuiltinTags.spaces

val extWorkBuiltinSpacesByName : Map<String, GtdSpace> = linkedMapOf<String, GtdSpace>().apply {
    extWorkBuiltinSpaces.forEach {
        this[it.name] = it
    }
}

// heap *****************************************************************************************
val Iterable<GtdTag>.spaces get() = filterIsInstance<GtdSpace>()