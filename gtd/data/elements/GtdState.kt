package org.freeplane.features.gtd.data.elements

class GtdState(override val clazz : GtdStateClass, num : GtdNum = GtdNum.NO) : GtdStatus(clazz, num) {
    override fun setNum(num: GtdNum): GtdState = create(clazz, num)

    fun setClazz(clazz: GtdStateClass) : GtdState = clazz.default

    companion object {
        fun create(clazz: GtdStateClass, num: GtdNum) =
            (allGtdStatusesMap[clazz]!![num] as GtdState?) ?: throw IllegalArgumentException()

        fun create(clazz: GtdStateClass, nums : Collection<GtdNum>) : List<GtdState> = arrayListOf<GtdState>().apply {
            nums.forEach { add(create(clazz, it)) }
        }

        fun valueOf(value: String) : GtdState {
            val (clazzStr, numStr) = value.split(Y)
            val clazz = GtdStateClass.valueOf(clazzStr)
            val num = GtdNum.valueOf(numStr)
            return create(clazz, num)
        }

        fun values(clazz: GtdStateClass) = (allGtdStatusesMap[clazz]?.values as Collection<GtdState>)

        val values by lazy {
            arrayListOf<GtdState>().apply {
                GtdStateClass.values().forEach {
                    addAll(values(it))
                }
            }
        }
    }
}

val GtdState.upperState : GtdState get() {
    if(!num.isDigit) throw IllegalArgumentException()

    val newNum = num.upperDigit
    val newState = runCatching { GtdState.create(clazz, newNum) }.getOrDefault(this)

    return newState
}

val GtdState.lowerState : GtdState get() {
    if(!num.isDigit) throw IllegalArgumentException()

    val newNum = num.lowerDigit
    val newState = runCatching { GtdState.create(clazz, newNum) }.getOrDefault(this)

    return newState
}