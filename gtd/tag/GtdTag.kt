package org.freeplane.features.gtd.tag

open class GtdTag(
    val name : String,
    val inheritedTags : Set<GtdTag> = hashSetOf()
) {
    val allInheritedTags : Set<GtdTag>
        get() = hashSetOf<GtdTag>().apply {
            inheritedTags.forEach {
                add(it)
                addAll(it.allInheritedTags)
            }
        }

    override fun toString() = name
}

// *************************************************************************************************
// SPACES-SPACES-SPACES-SPACES-SPACES-SPACES-SPACES-SPACES-SPACES-SPACES-SPACES-SPACES-SPACES-SPACES
// *************************************************************************************************
val GTD_SPACE_MAIN = GtdSpace("main_space")
// *************************************************************************************************
// PLANES-PLANES-PLANES-PLANES-PLANES-PLANES-PLANES-PLANES-PLANES-PLANES-PLANES-PLANES-PLANES-PLANES
// *************************************************************************************************
val GTD_PLANE_FAKE = GtdPlane("fake")

val GTD_PLANE_DEFAULT  = GtdPlane("default", totalCoverageType = true)

val GTD_PLANE_TRACKED  = GtdPlane("tracked")
val GTD_PLANE_MEMO  = GtdPlane("memo")

val GTD_PLANE_OBSERVATION  = GtdPlane("observation")
val GTD_PLANE_CART  = GtdPlane("cart")
val GTD_PLANE_BUFFER  = GtdPlane("buffer")

val GTD_PLANE_DELIVERY  = GtdPlane("delivery")

val GTD_PLANE_BLACK  = GtdPlane("black")
val GTD_PLANE_BLUE  = GtdPlane("blue")
val GTD_PLANE_RED  = GtdPlane("red")
val GTD_PLANE_GREEN  = GtdPlane("green")
val GTD_PLANE_YELLOW  = GtdPlane("yellow")

val GTD_PLANE_NO_PLANES = GtdPlane("no_planes")
// ***************************************************************************************************
// TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS-TAGS
// ***************************************************************************************************
val GTD_TAG_GCS_EXCLUDED = GtdTag("gcs_excluded")

val GTD_TAG_PERIODICAL  = GtdTag("periodical")

val GTD_TAG_ARTIFACT_REVIEW = GtdTag("artifact_review")
val GTD_TAG_REVIEW  = GtdTag("review")
val GTD_TAG_SYNC_REVIEW  = GtdTag("sync_review")
val GTD_TAG_LIGHT_REVIEW  = GtdTag("light_review")

val GTD_TAG_CHECK0  = GtdTag("check0")
val GTD_TAG_CHECK1  = GtdTag("check1")

val GTD_TAG_ARCHIVE = GtdTag("archive")

val GTD_TAG_BOOKMARK = GtdTag("bookmark")
val GTD_TAG_DIRECTION = GtdTag("direction")
val GTD_TAG_COLLECTOR = GtdTag("collector")

val GTD_TAG_PMC  = GtdTag("pmc")
val GTD_TAG_EMBED  = GtdTag("EMBED")
val GT_TAG_MINDDOC  = GtdTag("minddoc")

val GTD_TAG_NO_OTHER_TAGS = GtdTag("no_other_tags")
// ****************************************************************************************************
// ****************************************************************************************************
// ****************************************************************************************************
val workBuiltinTags : List<GtdTag> = arrayListOf<GtdTag>().apply {
    // spaces **********************
    add(GTD_SPACE_MAIN)
    // planes **********************
    add(GTD_PLANE_DEFAULT)

    add(GTD_PLANE_TRACKED)
    add(GTD_PLANE_MEMO)
    add(GTD_PLANE_OBSERVATION)
    add(GTD_PLANE_CART)
    add(GTD_PLANE_BUFFER)
    add(GTD_PLANE_DELIVERY)

    add(GTD_PLANE_BLACK)
    add(GTD_PLANE_BLUE)
    add(GTD_PLANE_RED)
    add(GTD_PLANE_GREEN)
    add(GTD_PLANE_YELLOW)
    // tags **********************
    add(GTD_TAG_PMC)
    add(GT_TAG_MINDDOC)
    add(GTD_TAG_EMBED)
    add(GTD_TAG_DIRECTION)
    add(GTD_TAG_COLLECTOR)
    add(GTD_TAG_PERIODICAL)
    add(GTD_TAG_ARTIFACT_REVIEW)
    add(GTD_TAG_REVIEW)
    add(GTD_TAG_SYNC_REVIEW)
    add(GTD_TAG_LIGHT_REVIEW)
    add(GTD_TAG_CHECK0)
    add(GTD_TAG_CHECK1)
    add(GTD_TAG_ARCHIVE)
    add(GTD_TAG_BOOKMARK)
}

val workBuiltinTagsByName : Map<String, GtdTag> = linkedMapOf<String, GtdTag>().apply {
    workBuiltinTags.forEach {
        this[it.name] = it
    }
}

val extWorkBuiltinTags : List<GtdTag> = arrayListOf<GtdTag>().apply {
    addAll(workBuiltinTags)

    add(GTD_PLANE_NO_PLANES)
    add(GTD_TAG_NO_OTHER_TAGS)
}

val extWorkBuiltinTagsByName : Map<String, GtdTag> = linkedMapOf<String, GtdTag>().apply {
    extWorkBuiltinTags.forEach {
        this[it.name] = it
    }
}

val outOfSets : List<GtdTag> = arrayListOf<GtdTag>().apply {
    // spaces **********************
    // planes **********************
    add(GTD_PLANE_FAKE)
    // tags **********************
    add(GTD_TAG_GCS_EXCLUDED)
}

// other tags **************************************************************************************
val workBuiltinOtherTags : List<GtdTag> = workBuiltinTags.toMutableList().apply { 
    removeAll(workBuiltinPlanes)
    removeAll(workBuiltinSpaces)
}

val extWorkBuiltinOtherTags : List<GtdTag> = extWorkBuiltinTags.toMutableList().apply {
    removeAll(extWorkBuiltinPlanes)
    removeAll(extWorkBuiltinSpaces)
}

val GtdTag.isOtherType get() = extWorkBuiltinOtherTags.contains(this)

val Iterable<GtdTag>.otherTags get() = filter(GtdTag::isOtherType)

// * * *
val Collection<GtdTag>.withInherited : Set<GtdTag> get() =
    hashSetOf<GtdTag>().also { withInheritedSet ->
        forEach {
            withInheritedSet.add(it)
            withInheritedSet.addAll(it.allInheritedTags)
        }
    }