package org.freeplane.features.gtd.conditions.base


enum class ContainsRequirement {
    NO_MATTER { override fun toString() = "" },
    INCLUDE { override fun toString() = "+" },
    EXCLUDE { override fun toString() = "-" };

    companion object
}