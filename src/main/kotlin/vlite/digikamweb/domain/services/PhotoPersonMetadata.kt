package vlite.digikamweb.domain.services

import vlite.core.backend.KExifToolA
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.photo.Photo

class PhotoPersonMetadata(
    override val exifTool: KExifToolA,
    override val photoFactory: PhotoFactoryA
) : PhotoPersonMetadataA,
    KExifToolA.Holder,
    PhotoFactoryA.Holder {

    companion object {

        /**
         * digiKam tag for people names with regions in the photo
         */
        private const val DIGIKAM_PEOPLE_TAG_NAME = "RegionPersonDisplayName"
    }

    private fun refresh(photo: Photo) = photo.withRefreshedMetadata().also {
        (photoFactory as? PhotoFactory.WithCache)?.refreshPhoto(it)
    }

    /**
     * [Useful Exiftool Commands (cheat sheet / examples)] (https://github.com/jonkeren/Exiftool-Commands)
     * ```
     * exiftool "-XMP:RegionPersonDisplayName+=John Doe" f9-start.jpeg
     * ```
     */
    override fun addPersonName(photo: Photo, personName: PersonName): Photo {
        exifTool.addXmpTagValue(photo.path, DIGIKAM_PEOPLE_TAG_NAME, personName.value)
        return refresh(photo)
    }

    /**
     * [Useful Exiftool Commands (cheat sheet / examples)] (https://github.com/jonkeren/Exiftool-Commands)
     * ```
     * exiftool "-XMP:RegionPersonDisplayName-=John Doe" f9-start.jpeg
     * ```
     */
    override fun removePersonName(photo: Photo, personName: PersonName): Photo {
        exifTool.removeXmpTagValue(photo.path, DIGIKAM_PEOPLE_TAG_NAME, personName.value)
        return refresh(photo)
    }

    /**
     * [Useful Exiftool Commands (cheat sheet / examples)] (https://github.com/jonkeren/Exiftool-Commands)
     *
     * (OPTIONS)[https://exiftool.org/exiftool_pod.html#OPTIONS]
     *
     * 2. Remove All Names from XMP:RegionPersonDisplayName
     * If you want to delete the entire tag (all people listed):
     *
     * exiftool "-XMP:RegionPersonDisplayName=" f9-start.jpeg
     */
    override fun removeAllPersonNames(photo: Photo): Photo {
        exifTool.removeXmpTag(photo.path, DIGIKAM_PEOPLE_TAG_NAME)
        return refresh(photo)
    }

}