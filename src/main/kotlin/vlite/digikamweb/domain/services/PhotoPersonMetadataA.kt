package vlite.digikamweb.domain.services

import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.photo.Photo

interface PhotoPersonMetadataA {

    fun addPersonName(photo: Photo, personName: PersonName): Photo
    fun removePersonName(photo: Photo, personName: PersonName): Photo
    fun removeAllPersonNames(photo: Photo): Photo

    interface Holder {
        val photoPersonMetadata : PhotoPersonMetadataA
    }

}