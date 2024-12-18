package org.freeplane.features.custom.zip

import net.lingala.zip4j.NativeStorage
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.model.enums.CompressionLevel
import net.lingala.zip4j.model.enums.CompressionMethod
import java.io.File

fun standardZipFolder(archiveName : String, folder : String, isIncludeRootFolder : Boolean = true) {
    val zipFile = ZipFile(archiveName)
    val zipParameters = ZipParameters().apply {
        compressionMethod = CompressionMethod.DEFLATE
        compressionLevel = CompressionLevel.NORMAL
        this.isIncludeRootFolder = isIncludeRootFolder
    }

    zipFile.addFolder(NativeStorage(File(folder)), zipParameters)
}