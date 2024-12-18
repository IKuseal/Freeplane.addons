package org.freeplane.features.custom.preprocess

import org.freeplane.features.custom.map.extensions.SpreadBuilder
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.custom.marks.IMarkContainer
import org.freeplane.features.custom.marks.MarkContainer
import org.freeplane.features.custom.marks.SimpleMarkGenerator
import org.freeplane.features.custom.pinneddata.IPinnedDataContainer
import org.freeplane.features.custom.pinneddata.PinnedDataContainer
import org.freeplane.features.custom.treestream.forEach
import org.freeplane.features.gtd.preprocess.XmlGtdLayer
import org.jsoup.nodes.Element

class XmlNodeModel(xml : Element,
                   var parent : XmlNodeModel? = null,
                   var map : XmlMapModel? = parent?.map
) : XmlElementModel(xml),
    IMarkContainer by MarkContainer(parent?.markGenerator ?: map ?: SimpleMarkGenerator()) ,
    IPinnedDataContainer by PinnedDataContainer()
{

    val extensions : MutableMap<Any, Any?> = hashMapOf()

    val text : String by lazy {
        var result = ""
        result = xml.attr(TEXT_ATTR)

        if(result.isEmpty()) {
            val richContent = childrenXmlWithTag("richcontent").firstOrNull()
                ?: return@lazy result
            result = richContent.text()
        }

        result
    }

    val image : XmlImageModel? by lazy {
        childrenXmlWithTag(XmlImageModel.TAG).firstOrNull {
            it.attr(XmlImageModel.URI_ATTR).contains("png")
                    || it.attr(XmlImageModel.URI_ATTR).contains("jpg")
        }?.let { XmlImageModel(it, this) }
    }

    val icons : Collection<XmlIconModel> by lazy {
        arrayListOf<XmlIconModel>().apply {
            childrenXmlWithTag(XmlIconModel.TAG).forEach {
                add(XmlIconModel(it, this@XmlNodeModel))
            }
        }
    }

    val attributes : Collection<XmlAttributeModel> by lazy {
        arrayListOf<XmlAttributeModel>().apply {
            childrenXmlWithTag(XmlAttributeModel.TAG).forEach {
                add(XmlAttributeModel(it, this@XmlNodeModel))
            }
        }
    }

    val isSummaryNode : Boolean = childrenXmlWithTag("hook")
        .firstOrNull {
            it.attr("NAME") == "SummaryNode"
                    || it.attr("NAME") == "FirstGroupNode"
        } != null

    // relations *************************************************************************************

    private val _children : MutableList<XmlNodeModel> by lazy {
        arrayListOf<XmlNodeModel>().apply {
            this@XmlNodeModel.xml.children().forEach {
                if(it.tagName() == TAG)
                    add(XmlNodeModel(it, this@XmlNodeModel))
            }
        }
    }

    val children : MutableList<XmlNodeModel> get() = _children.toMutableList()

    fun removeChild(child : XmlNodeModel) {
        if(!_children.contains(child)) throw IllegalArgumentException()
        _children.remove(child)
        child.parent = null
        child.map = null
        child.clearMarks()
        child.xml.remove()
    }

    fun remove() {
        parent?.let { it.removeChild(this) }
    }

    companion object {
        const val TAG = "node"
        const val TEXT_ATTR = "TEXT"
    }
}

fun XmlNodeModel.print() {
    stream().forEach { println(text) }
}