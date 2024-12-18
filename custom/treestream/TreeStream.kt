package org.freeplane.features.custom.treestream

import org.freeplane.features.custom.marks.IMarkContainer

// *****************************************************************************************************
// TREE-NODE-STREAM-TREE-NODE-STREAM-TREE-NODE-STREAM-TREE-NODE-STREAM-TREE-NODE-STREAM-TREE-NODE-STREAM
// *****************************************************************************************************
fun <T : Any> TreeNode<T>.asIterable() : Iterable<T> =
    arrayListOf<T>().also { list ->
        forEach {
            list.add(it)
        }
    }

val <T : Any> TreeNode<T>.ancestors : List<T> get() = arrayListOf<T>().also {
    var ancestor = parent
    while (ancestor != null) {
        it.add(ancestor.value)
        ancestor = ancestor.parent
    }
}

fun <T : Any> TreeNode<T>.takeAncestorsWhile(
predicate : TreeNode<T>.(T) -> Boolean
) : List<T> =
    arrayListOf<T>().also {
        var ancestor = parent
        while (ancestor != null && predicate(ancestor.value)) {
            it.add(ancestor.value)
            ancestor = ancestor.parent
        }
    }

val <T : Any> TreeNode<T>.chain : List<T> get() = arrayListOf<T>().also {
    it.add(value)
    it.addAll(ancestors)
}

val <T : Any> TreeNode<T>.descendants : List<T> get() =
    arrayListOf<T>().also { list ->
        toSkip = true
        forEach { list.add(it) }
}

val <T : Any> TreeNode<T>.branch : List<T> get() =
    arrayListOf<T>().also {
        it.add(value)
        it.addAll(descendants)
    }

fun <T : Any> TreeNode<T>.nextAncestor(condition : (T) -> Boolean) : T? {
    var ancestor : TreeNode<T>? = parent
    while(ancestor != null && !condition(ancestor.value)) {
        ancestor = ancestor.parent
    }

    return ancestor?.value
}

fun <T : Any> TreeNode<T>.forEach(action : TreeNode<T>.(T) -> Unit) {
    fun TreeNode<T>._do() {
        if(isStopped) return

        action(value)

        if(toContinue)
            children.forEach { if(!isStopped) it._do() else return }
    }

    if(!toSkip && isDepthWithinLimit) _do()
    else if(toContinue)
        children.forEach { if(!isStopped) it._do() else return }
}

fun <T : Any, S> TreeNode<T>.forEachSpread(
    initialValue : S,
    action : TreeNode<T>.(T, S) -> S
) {
    fun TreeNode<T>._do(spread : S) {
        if(isStopped) return

        val spread = action(value, spread)

        if(toContinue)
            children.forEach { if(!isStopped) it._do(spread) else return }
    }

    if(!toSkip && isDepthWithinLimit) _do(initialValue)
    else if(toContinue)
        children.forEach { if(!isStopped) it._do(initialValue) else return }
}

fun <T : Any, S> TreeNode<T>.fold(
    initialValue : S,
    fold : TreeNode<T>.(S, T) -> S
) : S {
    var currentValue : S = initialValue

    fun TreeNode<T>._do() {
        if(isStopped) return

        currentValue = fold(currentValue, value)

        if(toContinue)
            children.forEach { if(!isStopped) it._do() else return }
    }

    if(!toSkip && isDepthWithinLimit) _do()
    else if(toContinue)
        children.forEach { if(!isStopped) it._do() else return currentValue }

    return currentValue
}

fun <T : Any> TreeNode<T>.cut(
    predicate : TreeNode<T>.(T) -> Boolean
) {
    fun TreeNode<T>._do() {
        if(isStopped) return

        if (predicate(value)) remove()
        else if(toContinue)
            children.forEach { if(!isStopped) it._do() else return }
    }

    if(!toSkip && isDepthWithinLimit) _do()
    else if(toContinue)
        children.forEach { if(!isStopped) it._do() else return }
}

fun <T : Any> TreeNode<T>.filter(
    notChildrenForFiltered : Boolean = false,
    predicate : TreeNode<T>.(T) -> Boolean
) : List<T> = fold(arrayListOf<T>()) { list, it ->
    if (predicate(it)) {
        list.add(it)
        if (notChildrenForFiltered) isStoppedChildren = true
    }
    list
}

fun <T : Any> TreeNode<T>.count(
    predicate : TreeNode<T>.(T) -> Boolean
) : Int = filter(predicate = predicate).size

fun <T : Any, R : Any> TreeNode<T>.map(
    transform : TreeNode<T>.(T) -> R?
) : List<R?> = asIterable().map { transform(it) }

fun <T : Any, R : Any> TreeNode<T>.mapNotNull(
    transform : TreeNode<T>.(T) -> R?
) : List<R> = asIterable().mapNotNull { transform(it) }

// *******************************************************************************************************
// MARKING-MARKING-MARKING-MARKING-MARKING-MARKING-MARKING-MARKING-MARKING-MARKING-MARKING-MARKING-MARKING
// *******************************************************************************************************
fun <T : IMarkContainer> TreeNode<T>.mark(
    mark : String = value.generateMark(),
    predicate : TreeNode<T>.(T) -> Boolean
) : String {
    forEach {
        if(predicate(it)) it.addMark(mark)
    }

    return mark
}

fun <T : IMarkContainer> TreeNode<T>.markAncestors(
    mark : String = value.generateMark()
) : String {
    ancestors.forEach { it.addMark(mark) }

    return mark
}

fun <T : IMarkContainer> TreeNode<T>.markAncestorsWhile(
    mark : String = value.generateMark(),
    predicate : TreeNode<T>.(T) -> Boolean
) : String {
    takeAncestorsWhile(predicate).forEach { it.addMark(mark) }

    return mark
}

fun <T : IMarkContainer> TreeNode<T>.markAncestorsUntilFirstMarked(
    mark : String = value.generateMark()
) : String {
    takeAncestorsWhile { !it.checkMark(mark) }.forEach { it.addMark(mark) }

    return mark
}

// некоторые проблемы с ancestors, можно подняться за границы branch
fun <T : IMarkContainer> TreeNode<T>.fillMark(mark : String) {
    forEach {
        if(it.checkMark(mark))
            stream(it).markAncestorsUntilFirstMarked(mark)
    }
}

fun <T : IMarkContainer> TreeNode<T>.countMark(
    mark : String
) = count { it.checkMark(mark) }

fun <T : IMarkContainer> TreeNode<T>.cutMark(mark : String) {
    cut { it.checkMark(mark) }
}

fun <T : IMarkContainer> TreeNode<T>.cutNotMark(mark : String) {
    cut { !it.checkMark(mark) }
}

fun <T : IMarkContainer> TreeNode<T>.skeleton(
    ancestorsFlag : Boolean = true,
    descendantsFlag : Boolean = false,
    predicate : TreeNode<T>.(T) -> Boolean
) : T? {
    if(!ancestorsFlag) TODO()

    val passedConditionMark = value.generateMark()
    val passedAsAncestorMark = value.generateMark()
    val passedCondition : T.() -> Boolean = { checkMark(passedConditionMark) }
    val passedAsAncestor : T.() -> Boolean = { checkMark(passedAsAncestorMark) }
    val passedConditionOrAsAncestor : T.() -> Boolean = { passedAsAncestor() || passedCondition() }

    val nodesPassedCondition = stream(value).filter(predicate = predicate)

    nodesPassedCondition.forEach {
        it.addMark(passedConditionMark)
        stream(it).markAncestorsUntilFirstMarked(passedAsAncestorMark)
    }

    if(!value.passedConditionOrAsAncestor()) return null

    cut {
        if(it.passedCondition() && descendantsFlag) {
            isStoppedChildren = true
            false
        }
        else !it.passedConditionOrAsAncestor()
    }

    return this.value
}