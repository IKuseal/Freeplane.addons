package org.freeplane.features.custom

//fun <E> MutableList<E>.moveTo(element : E, index : Int) {
//    val currentIndex = indexOf(element)
//    var newIndex = index
//    removeAt(currentIndex)
//
//    if(index > currentIndex) --newIndex
//}

fun <E> MutableList<E>.swap(element1 : E, element2 : E) {
    val index1 = indexOf(element1)
    val index2 = indexOf(element2)
    swap(index1, index2)
}

fun <E> MutableList<E>.swap(index1 : Int, index2 : Int) {
    val el1 = get(index1)
    val el2 = get(index2)
    set(index1, el2)
    set(index2, el1)
}

data class IntInterval(var start : Int, var end : Int) {
    val isOnlyValue get() = start == end
}

val List<Int>.intervals get() : List<IntInterval> {
    val result = arrayListOf<IntInterval>()

    if(size == 0) return result

    var list = this.sorted()
    var next = list.first()
    var prev = next

    var interval = IntInterval(prev, next)
    result.add(interval)

    list = list.drop(1)

    if(list.size == 0) return result

    list.forEach {
        prev = next
        next = it
        if(prev != next - 1) {
            interval = IntInterval(next, next)
            result.add(interval)
        }
        else interval.end = next
    }

    return result
}

fun <E> compare(col1 : List<E>, col2 : List<E>, comparator : Comparator<in E>) : Int {
//    val col1 = col1.sortedWith(comparator)
//    val col2 = col2.sortedWith(comparator)

    val size = if(col1.size <= col2.size) col1.size else col2.size

    for(i in 0 until size) {
        val result = comparator.compare(col1[i], col2[i])
        if(result != 0) return result
    }

    return col1.size.compareTo(col2.size)
}

fun <E> createListComparator(comparator : Comparator<in E>) : Comparator<List<E>> =
    Comparator { o1, o2 -> compare(o1, o2, comparator) }

