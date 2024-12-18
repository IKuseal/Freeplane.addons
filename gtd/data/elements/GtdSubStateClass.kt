package org.freeplane.features.gtd.data.elements

import org.freeplane.features.gtd.data.elements.GtdNum.*

enum class GtdSubStateClass : GtdStatusClass {
    DSC {override val default: GtdSubState get() = GtdSubState.create(this, NO)
        override val spreadClass = DSCExt::class},
    CD {override val default: GtdSubState get() = GtdSubState.create(this, NO)
        override val spreadClass = CDExt::class};

    override fun invoke(): Collection<GtdSubState> = GtdSubState.values(this)

    abstract override val default: GtdSubState

    override fun invoke(num: GtdNum): GtdSubState = GtdSubState.create(this, num)

    override fun invoke(nums: Collection<GtdNum>) : List<GtdSubState> = GtdSubState.create(this, nums)

    override fun invoke(vararg nums: GtdNum): List<GtdSubState>  = GtdSubState.create(this, nums.toList())
}

interface CDExt : GtdStatusExt
interface DSCExt : GtdStatusExt