package org.freeplane.features.gtd.tag

class GtdPlane(
    name : String,
    inheritedTags : Set<GtdTag> = hashSetOf(),
    val totalCoverageType : Boolean = false
) : GtdTag(name, inheritedTags)

val workBuiltinPlanes : List<GtdPlane> = workBuiltinTags.planes

val workBuiltinPlanesByName : Map<String, GtdPlane> = linkedMapOf<String, GtdPlane>().apply {
    workBuiltinPlanes.forEach {
        this[it.name] = it
    }
}

val extWorkBuiltinPlanes : List<GtdPlane> = extWorkBuiltinTags.planes

val extWorkBuiltinPlanesByName : Map<String, GtdPlane> = linkedMapOf<String, GtdPlane>().apply {
    extWorkBuiltinPlanes.forEach {
        this[it.name] = it
    }
}

// heap *****************************************************************************************
val Iterable<GtdTag>.planes get() = filterIsInstance<GtdPlane>()

val builtinTotalCoveragePlanes = extWorkBuiltinPlanes.filter(GtdPlane::totalCoverageType)