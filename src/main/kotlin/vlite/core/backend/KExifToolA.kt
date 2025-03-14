package vlite.core.backend

import java.nio.file.Path

/**
 * [ExifTool by Phil Harvey](https://exiftool.org/)
 */
interface KExifToolA {

    fun version() : String

    fun allXmpData(photoPath: Path): List<String>
    fun addXmpTagValue(picturePath: Path, tagName: String, value: String, vararg otherOptions: String)
    fun removeXmpTagValue(picturePath: Path, tagName: String, value: String, vararg otherOptions: String)
    fun removeXmpTag(picturePath: Path, tagName: String, vararg otherOptions: String)

    interface Holder {
        val exifTool : KExifToolA
    }

}