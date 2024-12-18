package org.freeplane.features.custom.treestream

fun <T : Any, S> T.forEachWithSpread(
    children : T.() -> Iterable<T>,
    initial : S,
    depth : Int = Int.MAX_VALUE,
    action : T.(S) -> S)
{
    fun T._do(spread : S, currentDepth : Int) {
        val spread = action(spread)

        val childDepth = currentDepth + 1
        if(childDepth <= depth)
            children().forEach { it._do(spread, childDepth) }
    }

    val currentDepth = 1
    if(currentDepth <= depth) _do(initial, currentDepth)
}

fun <T : Any> T.forEach(
    children : T.() -> Iterable<T>,
    depth : Int = Int.MAX_VALUE,
    action : T.() -> Unit)
{
    fun T._do(currentDepth : Int) {
        action()

        val childDepth = currentDepth + 1
        if(childDepth <= depth)
            children().forEach { it._do(childDepth) }
    }

    val currentDepth = 1
    if(currentDepth <= depth) _do(currentDepth)
}

fun <T : Any> T.nextAncestor(parent: T.() -> T?, condition : (T) -> Boolean) : T? {
    var ancestor : T? = parent()
    while(ancestor != null && !condition(ancestor)) {
        ancestor = ancestor.parent()
    }

    return ancestor
}

fun <T : Any, S, U> T.forEachBiDirectional(
    children : T.() -> Iterable<T>,
    initial : S,
    upAction : T.(S) -> Pair<Boolean, S>,
    downAction : T.(List<Pair<T, U>>) -> U,
    depth : Int = Int.MAX_VALUE) : U?
{
    fun T._do(upSpread : S, currentDepth : Int) : U {
        val (toContinue, upSpread) = upAction(upSpread)

        val list = arrayListOf<Pair<T, U>>()

        val childDepth = currentDepth + 1
        if(toContinue && childDepth <= depth) {
            children().forEach {
                val downSpread = it._do(upSpread, childDepth)
                list.add(it to downSpread)
            }
        }

        return downAction(list)
    }

    val currentDepth = 1
    if(currentDepth <= depth) return _do(initial, currentDepth)
    else return null
}

fun <T : Any> T.cut(
    children : T.() -> Iterable<T>,
    remove : T.() -> Unit,
    fromRoot : Boolean = false,
    predicate : (T) -> Boolean
) {
    fun T._do() {
        if (predicate(this)) remove()
        else children().forEach { it._do() }
    }

    if(fromRoot) _do()
    else children().forEach { it._do() }
}

fun <T : Any> T.ancestors(parent : T.() -> T?) : List<T> = arrayListOf<T>().also {
    var ancestor = parent()
    while (ancestor != null) {
        it.add(ancestor)
        ancestor = ancestor.parent()
    }
}

fun <T : Any> T.treeLine(parent : T.() -> T?) : List<T> = arrayListOf<T>().also {
    it.add(this)
    it.addAll(ancestors(parent))
}
