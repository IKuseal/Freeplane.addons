package org.freeplane.features.custom.space

import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.url.UrlManager
import java.io.File
import java.net.URL

object SpaceController {

    const val SPACE_FOLDER_PATH = "C://Users/serge/Dropbox/freeplane"
    val SERVICE_FOLDER_PATH get() = "${GlobalFrFacade.resourceController.freeplaneUserDirectory}/.service"

    init {
        File(SERVICE_FOLDER_PATH).mkdir()
    }

    private const val mapExt = ".mm"

    val maps : Collection<MapFile>
        get() = mapFiles.map(::MapFile)

    val mapFiles : Collection<File>
        get() {
            val spaceFolder = File(SPACE_FOLDER_PATH)
            val listFiles = spaceFolder.listFiles { dir, name -> name.endsWith(UrlManager.FREEPLANE_FILE_EXTENSION) }
                ?: arrayOf()

            return listFiles.toList()
        }

    fun getMapFile(name: String, containsExtKey: Boolean = false) : File? {
        val fullName = if(containsExtKey) name else "$name$mapExt"
        return mapFiles.firstOrNull { it.name == fullName }
    }

    fun getMap(name: String, containsExtKey: Boolean = false) : MapFile? =
        getMapFile(name, containsExtKey)?.let { MapFile(it) }

    fun getUrlOfMap(name: String, containsExtKey: Boolean = false) : URL? =
        getMapFile(name, containsExtKey)?.toURI()?.toURL()
}