package org.freeplane.features.custom.marks

interface IMarkContainer {
    fun addMark(mark: String): Boolean
    fun removeMark(mark: String): Boolean
    fun checkMark(mark: String): Boolean
    var markGenerator: IMarkGenerator
    fun generateMark(): String
    fun clearMarks()
}