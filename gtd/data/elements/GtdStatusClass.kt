package org.freeplane.features.gtd.data.elements

import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

sealed interface GtdStatusClass {
    val default : GtdStatus
    val spreadClass : KClass<out GtdStatusExt>

    val name : String

    operator fun invoke() : Collection<GtdStatus> = GtdStatus.values(this)

    operator fun invoke(num : GtdNum) = GtdStatus.create(this, num)

    operator fun invoke(nums : Collection<GtdNum>) = GtdStatus.create(this, nums)

    operator fun invoke(vararg nums : GtdNum) = GtdStatus.create(this, nums.toList())

    companion object {
        fun valueOf(value : String) : GtdStatusClass {
            runCatching { GtdStateClass.valueOf(value) }.getOrNull()?.let { return it }
            runCatching { GtdSubStateClass.valueOf(value) }.getOrNull()?.let { return it }

            throw IllegalArgumentException()
        }

        val values get() = arrayListOf<GtdStatusClass>().apply {
            addAll(GtdStateClass.values())
            addAll(GtdSubStateClass.values())
        }
    }
}