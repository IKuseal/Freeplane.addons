package org.freeplane.features.logistics.collector

import org.freeplane.features.custom.space.SpaceController
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object DataToCollectAccessor {
    private val tempImageFile get() =
        File("${SpaceController.SERVICE_FOLDER_PATH}/DataToCollectAccessorImage.png")
            .apply {
                createNewFile()
            }

    private val tk get() = Toolkit.getDefaultToolkit()

    private val clipboard get() = tk.systemClipboard

    fun getDataAsString() =
        if(isTextData) getText()
        else if(isImageData) "IMAGE"
        else if(isLinkData) getText()
        else "ERROR"

    val isTextData
        get() = !isLinkData && clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)

    val isImageData
        get() = clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)

    val isLinkData
        get() = clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor) && getText().startsWith("http")

    fun getText() =
        clipboard.getData(DataFlavor.stringFlavor) as String

    fun getLink() = getText()

    fun writeImageToFile() : File {
        val image = clipboard.getData(DataFlavor.imageFlavor) as BufferedImage
        ImageIO.write(image,"PNG", tempImageFile)
        return tempImageFile
    }

    fun getImageFile() = tempImageFile
}