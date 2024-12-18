package org.freeplane.features.custom.image

import org.freeplane.features.custom.space.SpaceController
import java.io.File
import kotlin.random.Random

object CustomImagesStore {
    private val folderPath get() = SpaceController.SPACE_FOLDER_PATH

    fun getImage(imageId : String) = File("$folderPath/$imageId")

    fun getImageSafe(imageId : String) = File("$folderPath/$imageId").takeIf { it.exists() }

    fun generateName() = "image_${Random.Default.nextLong()}"
}