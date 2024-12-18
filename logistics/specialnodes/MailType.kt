package org.freeplane.features.logistics.specialnodes

import org.freeplane.features.map.NodeModel

enum class MailType {
    MAIL {
        override fun of(node: NodeModel) = node.mailNode

        override fun ofWithCreate(node: NodeModel) = node.createMailNode()
         },
    MAIL2 {
        override fun of(node: NodeModel) = node.mailNode2

        override fun ofWithCreate(node: NodeModel) = node.createMailNode2()
    };

    abstract fun of(node : NodeModel) : NodeModel?

    abstract fun ofWithCreate(node : NodeModel) : NodeModel
}