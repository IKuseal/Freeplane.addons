package org.freeplane.features.gtd.data.elements

class GtdSubState(override val clazz : GtdSubStateClass, num : GtdNum = GtdNum.NO) : GtdStatus(clazz, num) {
    override fun setNum(num: GtdNum): GtdSubState = create(clazz, num)

    fun setClazz(clazz: GtdSubStateClass) : GtdSubState = clazz.default

    companion object {
        fun create(clazz: GtdSubStateClass, num: GtdNum) =
            (allGtdStatusesMap[clazz]!![num] as GtdSubState?) ?: throw IllegalArgumentException()

        fun create(clazz: GtdSubStateClass, nums : Collection<GtdNum>) : List<GtdSubState> = arrayListOf<GtdSubState>().apply {
            nums.forEach { add(create(clazz, it)) }
        }

        fun valueOf(value: String) : GtdSubState {
            val (clazzStr, numStr) = value.split(Y)
            val clazz = GtdSubStateClass.valueOf(clazzStr)
            val num = GtdNum.valueOf(numStr)
            return create(clazz, num)
        }

        fun values(clazz: GtdSubStateClass) = (allGtdStatusesMap[clazz]?.values as Collection<GtdSubState>)

        val values by lazy { arrayListOf<GtdSubState>().apply {
            GtdSubStateClass.values().forEach {
                addAll(values(it))
            }
        } }
    }
}