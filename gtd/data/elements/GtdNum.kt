package org.freeplane.features.gtd.data.elements

import org.freeplane.features.custom.intervals
import org.freeplane.features.gtd.data.elements.GtdNum.*
import org.freeplane.features.gtd.data.special.GtdNumAlone
import org.freeplane.features.gtd.data.special.GtdNumFullSet
import org.freeplane.features.gtd.data.special.GtdNumInterval
import org.freeplane.features.gtd.data.special.GtdNumRange

enum class GtdNum {
    NO { override fun toString() = "#" },
    S0 { override fun toString() = "0" },
    S1 { override fun toString() = "1" },
    S2 { override fun toString() = "2" },
    S3 { override fun toString() = "3" },
    S4 { override fun toString() = "4" },
    S5 { override fun toString() = "5" },
    S6 { override fun toString() = "6" },
    S7 { override fun toString() = "7" },
    S8 { override fun toString() = "8" },
    S9 { override fun toString() = "9" },
    V0 { override fun toString() = "V0" },
    V1 { override fun toString() = "V1" },
    V2 { override fun toString() = "V2" },
    T  { override fun toString() = "T" },
    ND { override fun toString() = "ND" };

    val isDigit get() = "$this".toIntOrNull() != null

    val digit get() = "$this".toIntOrNull() ?: throw IllegalArgumentException()

    companion object {
        val digits = arrayListOf(S0, S1, S2, S3, S4, S5, S6, S7, S8, S9)
        val s0s5Digits = arrayListOf(S0, S1, S2, S3, S4, S5)

        fun fromDigit(digit : Int) : GtdNum {
            digit.takeIf { it in 0..9 } ?: throw IllegalArgumentException()
            return GtdNum.values()[1 + digit]
        }
    }
}

val GtdNum.upperDigit : GtdNum get() {
    if(!GtdNum.digits.contains(this)) throw IllegalArgumentException()
    if(this == S9) return S9

    return GtdNum.values()[ordinal + 1]
}

val GtdNum.lowerDigit : GtdNum get() {
    if(!GtdNum.digits.contains(this)) throw IllegalArgumentException()
    if(this == S0) return S0

    return GtdNum.values()[ordinal - 1]
}

fun Companion.intervals(nums : Collection<GtdNum>) : List<GtdNumRange> {
    val result = arrayListOf<GtdNumRange>()

    val list = nums.sorted()

    val resultHead = arrayListOf<GtdNumRange>()
    val resultMediate = arrayListOf<GtdNumRange>()
    val resultTail = arrayListOf<GtdNumRange>()

    list.takeWhile { !it.isDigit }
        .forEach { resultHead.add(GtdNumAlone(it)) }
    list.takeLastWhile { !it.isDigit }
        .forEach { resultTail.add(GtdNumAlone(it)) }

    if(resultHead.size + resultTail.size != list.size) {
        val digitGtdNums = list.subList(resultHead.size, list.size - resultTail.size)

        digitGtdNums
            .map { it.digit }
            .intervals
            .map {
                if(it.isOnlyValue) GtdNumAlone(GtdNum.fromDigit(it.start))
                else GtdNumInterval(
                    GtdNum.fromDigit(it.start),
                    GtdNum.fromDigit(it.end)
                )
            }
            .forEach { resultMediate.add(it) }
    }

    result.apply {
        addAll(resultHead)
        addAll(resultMediate)
        addAll(resultTail)
    }

    return result
}

fun Companion.intervals(nums : Collection<GtdNum>, fullSet : Collection<GtdNum>) =
    if(nums.size == fullSet.size) arrayListOf<GtdNumRange>(GtdNumFullSet)
    else intervals(nums)

fun Companion.intervals(nums : Collection<GtdNum>, clazz : GtdStatusClass) =
    intervals(nums, validNums[clazz]!!)
