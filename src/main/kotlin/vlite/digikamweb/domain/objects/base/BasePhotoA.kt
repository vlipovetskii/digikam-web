package vlite.digikamweb.domain.objects.base

import com.drew.metadata.Metadata
import vlite.core.backend.dateTimeOriginal
import vlite.core.backend.xmpProperties
import vlite.core.domain.objects.MimeType
import kotlin.io.path.exists
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readBytes

abstract class BasePhotoA<TPhotoName : BasePhotoNameA> : HasPathA {

    abstract val name : TPhotoName

    val strNameWithoutExtension get() = path.nameWithoutExtension
    val imageData get() = path.readBytes()
    // val imageDataAsBase64 get() = imageData.imageAsBase64()
    // val imageDataMd5 get() = KMd5.compute(imageData)

    companion object {
        val MIME_TYPE = MimeType.IMAGE_JPEG
    }

    abstract class WithMetadata<TPhotoName : BasePhotoNameA>(val imageMetadata: Metadata) : BasePhotoA<TPhotoName>() {

        /**
         * PRB: NullPointerException: Parameter specified as non-null is null
         * WO: (val imageMetadata: Metadata)
         */
        //val imageMetadata = path.imageMetadata

        val xmpProperties get() = imageMetadata.xmpProperties
        val dateTimeOriginal get() = imageMetadata.dateTimeOriginal

    }

    val exists get() = path.exists()

}