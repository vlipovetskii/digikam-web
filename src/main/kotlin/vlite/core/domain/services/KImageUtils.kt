@file:Suppress("unused")

package vlite.core.domain.services

import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam

@Suppress("EnumEntryName")
enum class KImageFormat {
    gif,
    jpeg,
    png,
    ;

    companion object {
        val DEFAULT = jpeg
        val ALL = entries
    }
}

fun ByteArray.imageAsBase64(format: KImageFormat = KImageFormat.DEFAULT) =
    "data:${format.name};base64,${Base64.getEncoder().encodeToString(this)}"

const val TRANSPARENT_GIF_1PX = "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACwAAAAAAQABAAACAkQBADs="

// Based on ChatGPT response
fun InputStream.imageToFormat(
    outputStream: OutputStream,
    format: KImageFormat = KImageFormat.DEFAULT,
    quality: Float = 0.9f
) {
    val inputStream = this
    val bufferedImage: BufferedImage = ImageIO.read(inputStream)

    // Convert and write the JPEG image to the output stream
    val jpgWriter = ImageIO.getImageWritersByFormatName("jpeg").next()
    val jpgParams = jpgWriter.defaultWriteParam

    if (jpgParams.canWriteCompressed()) {
        jpgParams.compressionMode = ImageWriteParam.MODE_EXPLICIT
        jpgParams.compressionQuality = quality // Quality range 0.0 (low) to 1.0 (high)
    }

    jpgWriter.output = ImageIO.createImageOutputStream(outputStream)
    jpgWriter.write(null, IIOImage(bufferedImage, null, null), jpgParams)

}

fun ByteArray.toFormat(format: KImageFormat = KImageFormat.DEFAULT): ByteArray {
    inputStream().use { inputStream ->
        ByteArrayOutputStream().use { outputStream ->
            inputStream.imageToFormat(outputStream, format)
            return outputStream.toByteArray()
        }
    }
}