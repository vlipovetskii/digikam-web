package vlite.digikamweb.domain.services

import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.objects.photo.Photo
import java.nio.file.Path

open class PhotoFactory : PhotoFactoryA {

    companion object : KLoggerA {
        private val log by lazy { classLogger }
    }

    override fun photo(path: Path) = Photo(path)

    override fun personPhoto(path: Path) = PersonPhoto(path)

    override fun remove(photo: Photo) {
        @Suppress("LoggingSimilarMessage")
        log.info("remove(\"$photo\")")
    }

    override fun remove(personPhoto: PersonPhoto) {
        @Suppress("LoggingSimilarMessage")
        log.info("remove(\"$personPhoto\")")
    }

    interface WithCache {
        fun refreshPhoto(photo: Photo)
    }

    class WithMemoryCache : PhotoFactory(), WithCache {

        private val photoCache = mutableMapOf<Path, Photo>()
        private val personPhotoCache = mutableMapOf<Path, PersonPhoto>()

        override fun refreshPhoto(photo: Photo) {
            photoCache[photo.path] = photo
        }

        override fun photo(path: Path) = photoCache.computeIfAbsent(path) { super.photo(path) }

        override fun personPhoto(path: Path) = personPhotoCache.computeIfAbsent(path) { super.personPhoto(path) }

        override fun remove(photo: Photo) {
            super.remove(photo)
            photoCache.remove(photo.path)
        }

        override fun remove(personPhoto: PersonPhoto) {
            super.remove(personPhoto)
            photoCache.remove(personPhoto.path)
        }

    }

}