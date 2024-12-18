package org.freeplane.features.gtd.glob.globspace

import org.freeplane.features.custom.map.MapLifecycleState
import org.freeplane.features.custom.preprocess.XmlMapModel
import org.freeplane.features.custom.space.MapFile
import org.freeplane.features.custom.space.SpaceController
import org.freeplane.features.gtd.glob.GlobModuleData
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.map.MapModel
import org.freeplane.features.url.mindmapmode.MapLifecycleConductor
import java.io.File
import java.net.URL

object MapModulesImporter {
    private const val serviceMapId = "ImportingModulesGtdGlobServiceFile"
    private val serviceFile = SpaceController.getMapFile(serviceMapId)!!

    fun import(mapFile : MapFile) : Collection<GlobModuleData> {
        val xmlMap = XmlMapModel.create(mapFile)
        MapGlobSkeletonCalculator.invoke(xmlMap)
        writeSkeletonVersion(xmlMap, serviceFile)
        val map = loadMap(serviceFile, mapFile.file.toURI().toURL())

        val result = arrayListOf<GlobModuleData>().apply {
            val root = map.rootNode
            if(root.gtdNode.module != null) {
                add(GlobModuleData(root))
            }
            else root.children.map(::GlobModuleData).forEach(this::add)
        }

        return result
    }

    private fun writeSkeletonVersion(xmlMap: XmlMapModel, file : File) {
        file.writer(Charsets.UTF_8).run {
            write(xmlMap.xml.toString())
            flush()
        }
    }

    private fun loadMap(file: File, targetUrl : URL) : MapModel =
        MapLifecycleConductor().let {
            it.load(file)
            it.targetState = MapLifecycleState.STARTED
            it.doJob()
        }.apply {
            url = targetUrl
        }
}