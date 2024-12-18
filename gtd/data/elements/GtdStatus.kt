package org.freeplane.features.gtd.data.elements

import org.freeplane.features.gtd.data.elements.GtdNum.*
import org.freeplane.features.gtd.data.elements.GtdStateClass.*
import org.freeplane.features.gtd.data.elements.GtdSubStateClass.CD
import org.freeplane.features.gtd.data.elements.GtdSubStateClass.DSC

sealed class GtdStatus(open val clazz : GtdStatusClass, val num: GtdNum = NO) {
    abstract fun setNum(num : GtdNum) : GtdStatus

    val name: String get() = "${clazz.name}$Y${num.name}"

    override fun toString() = clazz.name + num.toString()

    companion object {
        const val Y = ":"

        val values : Collection<GtdStatus> by lazy {
            arrayListOf<GtdStatus>().apply {
                addAll(GtdState.values)
                addAll(GtdSubState.values)
        } }

        fun create(clazz : GtdStatusClass, num : GtdNum) : GtdStatus = when(clazz) {
            is GtdStateClass -> GtdState.create(clazz, num)
            is GtdSubStateClass -> GtdSubState.create(clazz, num)
        }

        fun create(clazz: GtdStatusClass, nums : Collection<GtdNum>) : List<out GtdStatus> = when(clazz) {
            is GtdStateClass -> GtdState.create(clazz, nums)
            is GtdSubStateClass -> GtdSubState.create(clazz, nums)
        }

        fun values(clazz : GtdStatusClass) : Collection<out GtdStatus> = when(clazz) {
            is GtdStateClass -> GtdState.values(clazz)
            is GtdSubStateClass -> GtdSubState.values(clazz)
        }
    }
}

val validNums = hashMapOf<GtdStatusClass, List<GtdNum>>().apply {
    val digits = GtdNum.digits.toTypedArray()
    val halfDigits = GtdNum.s0s5Digits.toTypedArray()

    put(F, arrayListOf(NO))
    put(I, arrayListOf(NO, ND, V0, V1, V2))
    put(I2, arrayListOf(NO, ND, V0, V1, V2))
    put(R, arrayListOf(*halfDigits, T, ND))
    put(C, arrayListOf(NO))
    put(L, arrayListOf(*digits, T, ND, V0, V1, V2))

//    put(Z,  arrayListOf(*halfDigits, T, ND))
    put(Y2, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(Y0, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(W2, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(W1, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(W0, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(E2, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(E1, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(E0, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(S,  arrayListOf(*digits,     T, ND, V0, V1, V2))
    put(U2, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(U0, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(O,  arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(Q2, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(Q1, arrayListOf(*halfDigits, T, ND, V0, V1, V2))
    put(Q0, arrayListOf(*halfDigits, T, ND, V0, V1, V2))

    put(DSC, arrayListOf(NO))
    put(CD, arrayListOf(NO))
}

fun GtdStatusClass.isNumValid(num : GtdNum) = validNums[this]!!.contains(num)

val allGtdStatusesMap = hashMapOf<GtdStatusClass, Map<GtdNum, GtdStatus>>().apply {
    GtdStateClass.values().forEach { stateClass ->
        val stateMap = linkedMapOf<GtdNum, GtdStatus>()
        put(stateClass, stateMap)

        validNums[stateClass]!!.forEach { num ->
            stateMap[num] = GtdState(stateClass, num)
        }
    }

    GtdSubStateClass.values().forEach { subStateClass ->
        val subStateMap = linkedMapOf<GtdNum, GtdStatus>()
        put(subStateClass, subStateMap)

        validNums[subStateClass]!!.forEach { num ->
            subStateMap[num] = GtdSubState(subStateClass, num)
        }
    }
}
