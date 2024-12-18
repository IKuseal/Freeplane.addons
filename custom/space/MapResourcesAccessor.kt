package org.freeplane.features.custom.space

import java.io.File

class MapResourcesAccessor(private val mapId : String) {
    val folder : File
        get() = File("${SpaceController.SPACE_FOLDER_PATH}/${mapId}_files").apply { mkdirs() }

    fun putImage(file: File, newName: String?) : File {
        val newName = newName ?: file.name
        return file.copyTo(File(folder, newName), true)
    }
}