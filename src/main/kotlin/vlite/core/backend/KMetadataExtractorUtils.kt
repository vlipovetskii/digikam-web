package vlite.core.backend

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.xmp.XmpDirectory
import vlite.core.domain.services.toLocalDateTime
import java.nio.file.Path

val Path.imageMetadata: Metadata
    get() = ImageMetadataReader.readMetadata(toFile())

inline fun <reified TDirectory : Directory> Metadata.firstDirectoryOfType(): TDirectory? =
    getFirstDirectoryOfType(TDirectory::class.java)

val Metadata.xmpProperties: Map<String, String>? get() = firstDirectoryOfType<XmpDirectory>()?.xmpProperties

val Metadata.dateTimeOriginal get() = firstDirectoryOfType<ExifSubIFDDirectory>()?.dateOriginal?.toLocalDateTime()
