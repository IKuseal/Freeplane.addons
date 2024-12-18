package org.freeplane.features.custom.test

import org.freeplane.core.extension.IExtension
import org.freeplane.features.custom.map.getExt
import org.freeplane.features.custom.map.putExt
import org.freeplane.features.map.NodeModel

class TestingMarkExtension(val type : TestingMarkType) : IExtension

var NodeModel.testingMarkExtension : TestingMarkExtension?
    get() = getExt(TestingMarkExtension::class)
    set(value) {
        putExt(TestingMarkExtension::class, value)
    }