package org.freeplane.features.custom.marks

class SimpleMarkGenerator : IMarkGenerator {
    private var i = 0

    override fun generateMark() = "${i++}"
}