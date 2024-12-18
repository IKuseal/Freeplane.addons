package org.freeplane.features.gtd.module

import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.node.GtdNodeModel

data class GtdModule(var layer0ToExport : Boolean = true,
                     var layer1ToExport : Boolean = true) {
    var idSupplier : () -> String = {""}

    var id : String = ""
        get() = idSupplier()
}

fun GtdModule.setIdWith(gtdNode : GtdNodeModel) {
    idSupplier = {GtdController.getIdUsingLinkToNode(gtdNode.node)}
}