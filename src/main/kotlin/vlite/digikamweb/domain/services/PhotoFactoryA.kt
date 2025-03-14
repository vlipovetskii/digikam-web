package vlite.digikamweb.domain.services

import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.objects.photo.Photo
import java.nio.file.Path

interface PhotoFactoryA {

    fun photo(path: Path) : Photo
    fun personPhoto(path: Path) : PersonPhoto

    fun remove(photo: Photo)
    fun remove(personPhoto: PersonPhoto)

    interface Holder {
        val photoFactory: PhotoFactoryA
    }

}