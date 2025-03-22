package vlite.digikamweb.ui.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.digikamweb.domain.objects.AlbumName
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.PhotoName
import vlite.digikamweb.domain.objects.TenantName
import vlite.digikamweb.domain.objects.base.BasePhotoA
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.domain.services.storage.TenantStorageA
import vlite.digikamweb.ui.controllers.ImageController.Companion.ROUTE
import java.io.IOException

/**
 * [Hosting REST endpoints next to your Vaadin UI](https://vaadin.com/blog/hosting-rest-endpoints-next-to-your-vaadin-ui)
 * [Prevent Handling of Specific URLs](https://vaadin.com/docs/latest/flow/integrations/spring/configuration#prevent-handling-of-specific-urls)
 * [Spring @PathVariable Annotation](https://www.baeldung.com/spring-pathvariable)
 */
@RestController
@RequestMapping("/$ROUTE")
class ImageController(
    override val tenantStorage: TenantStorageA
) : TenantStorageA.Holder {

    companion object : KLoggerA {

        private const val ROUTE = "image"
        private const val PHOTO_ROUTE = "photo"
        private const val PERSON_PHOTO_ROUTE = "person-photo"

        /**
         * tenantName/albumName/photoName
         */
        fun urlPath(photo: Photo) =
            photo.run {
                "$ROUTE/$PHOTO_ROUTE/${tenantName.value}/${albumName.value}/${name.value}"
            }
        /**
         * tenantName/personName
         */
        fun urlPath(personPhoto: PersonPhoto) =
            personPhoto.run {
                "$ROUTE/$PERSON_PHOTO_ROUTE/${tenantName.value}/${name.value}"
            }

        private val log by lazy { classLogger }

        private const val TENANT_NAME = "{${TenantName.ATTRIBUTE_NAME}}"
        private const val ALBUM_NAME = "{${AlbumName.ATTRIBUTE_NAME}}"
        private const val PHOTO_NAME = "{${PhotoName.ATTRIBUTE_NAME}}"
        private const val PERSON_NAME = "{${PersonName.ATTRIBUTE_NAME}}"

    }

    private fun BasePhotoA<*>.getImage(): ResponseEntity<ByteArray> {
        return try {
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, BasePhotoA.MIME_TYPE.toString())
                .body(imageData)
        } catch (e: IOException) {
            log.error(e.stackTraceToString())
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    private fun String.errorResponse(): ResponseEntity<ByteArray> {
        log.error(this)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @Suppress("unused")
    @GetMapping("/$PHOTO_ROUTE/$TENANT_NAME/$ALBUM_NAME/$PHOTO_NAME")
    fun getPhoto(
        @PathVariable tenantName: TenantName,
        @PathVariable albumName: AlbumName,
        @PathVariable photoName: PhotoName
    ): ResponseEntity<ByteArray> {
        return tenantStorage.photoStorage(tenantName).photo(albumName, photoName)
            ?.getImage()
            ?: "Photo for `${tenantName.value}/${albumName.value}/${photoName.value}` not found".errorResponse()
    }

    @Suppress("unused")
    @GetMapping("/$PERSON_PHOTO_ROUTE/$TENANT_NAME/$PERSON_NAME")
    fun getPersonPhoto(
        @PathVariable tenantName: TenantName,
        @PathVariable personName: PersonName
    ): ResponseEntity<ByteArray> {
        return tenantStorage.photoStorage(tenantName).personPhoto(personName)
            ?.getImage()
            ?: "Person photo for `${tenantName.value}/${personName.value}` not found".errorResponse()
    }

}
