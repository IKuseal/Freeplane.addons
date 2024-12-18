package org.freeplane.features.custom.marks

class MarkContainer(generator: IMarkGenerator) : IMarkContainer {
    private val marks : MutableSet<String> = mutableSetOf()

    override fun addMark(mark : String) = marks.add(mark)

    override fun removeMark(mark : String) = marks.remove(mark)

    override fun checkMark(mark : String) = marks.contains(mark)

    override var markGenerator: IMarkGenerator = generator

    override fun generateMark() = markGenerator.generateMark()

    override fun clearMarks() = marks.clear()
}