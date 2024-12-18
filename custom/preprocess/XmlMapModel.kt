package org.freeplane.features.custom.preprocess

import org.freeplane.features.ankikt.xmlentity.XmlEntityMapAiExtension
import org.freeplane.features.custom.marks.IMarkGenerator
import org.freeplane.features.custom.marks.SimpleMarkGenerator
import org.freeplane.features.custom.space.MapFile
import org.freeplane.features.custom.treestream.forEach
import org.freeplane.features.gtd.preprocess.XmlGtdConfiguration
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import java.io.File

class XmlMapModel(xml : Element) : XmlElementModel(xml), IMarkGenerator by SimpleMarkGenerator() {
    val root : XmlNodeModel by lazy {
        xml.children()
            .first { it.tagName() == XmlNodeModel.TAG }
            .let { XmlNodeModel(it, map = this) }
    }

    val gtdConfiguration : XmlGtdConfiguration? by lazy {
        childXmlWithTag(XmlGtdConfiguration.TAG)?.let { XmlGtdConfiguration(it, this) }
    }

    val aiExtension : XmlEntityMapAiExtension? by lazy {
        childXmlWithTag(XmlEntityMapAiExtension.TAG)?.let { XmlEntityMapAiExtension(it, this) }
    }

    companion object {
        fun create(file : File) = XmlMapModel(
            Jsoup.parse(file.readText(), Parser.xmlParser())
                .children().first()!!
        )

        fun create(mapFile : MapFile) = create(mapFile.file)
    }
}

val XmlMapModel.allIcons : Set<XmlIconModel> get() = hashSetOf<XmlIconModel>().apply {
    this@allIcons.root.stream().forEach {
        addAll(it.icons)
    }
}