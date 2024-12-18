package org.freeplane.features.gtd.data.elements

import org.freeplane.features.gtd.data.elements.GtdNum.*
import kotlin.reflect.KClass

enum class GtdStateClass : GtdStatusClass {
    F { override val default: GtdState get() = GtdState.create(this, NO) }, // fake
    I { override val default: GtdState get() = GtdState.create(this, NO) },
    I2 { override val default: GtdState get() = GtdState.create(this, NO) },
    R { override val default: GtdState get() = GtdState.create(this, S5) },
    C { override val default: GtdState get() = GtdState.create(this, NO) },
//    Z  { override val default: GtdState get() = GtdState.create(this, S0) },
    Y2 { override val default: GtdState get() = GtdState.create(this, S0) },
    Y0 { override val default: GtdState get() = GtdState.create(this, S0) },
    W2 { override val default: GtdState get() = GtdState.create(this, S0) },
    W1 { override val default: GtdState get() = GtdState.create(this, S0) },
    W0 { override val default: GtdState get() = GtdState.create(this, S0) },
    E2 { override val default: GtdState get() = GtdState.create(this, S0) },
    E1 { override val default: GtdState get() = GtdState.create(this, S0) },
    E0 { override val default: GtdState get() = GtdState.create(this, S0) },
    S  { override val default: GtdState get() = GtdState.create(this, S0) },
    U2 { override val default: GtdState get() = GtdState.create(this, S0) },
    U0 { override val default: GtdState get() = GtdState.create(this, S0) },
    O  { override val default: GtdState get() = GtdState.create(this, S0) },
    Q2 { override val default: GtdState get() = GtdState.create(this, S0) },
    Q1 { override val default: GtdState get() = GtdState.create(this, S0) },
    Q0 { override val default: GtdState get() = GtdState.create(this, S0) },
    L { override val default: GtdState get() = GtdState.create(this, S0) };

    abstract override val default : GtdState

    override val spreadClass: KClass<out GtdStatusExt> = GtdStateExt::class

    override fun invoke(): Collection<GtdState> = GtdState.values(this)

    override fun invoke(num: GtdNum): GtdState = GtdState.create(this, num)

    override fun invoke(nums: Collection<GtdNum>) : List<GtdState> = GtdState.create(this, nums)

    override fun invoke(vararg nums: GtdNum): List<GtdState>  = GtdState.create(this, nums.toList())
}

interface GtdStateExt : GtdStatusExt