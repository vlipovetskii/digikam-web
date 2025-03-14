package vlite.digikamweb.domain.services.storage

import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.objects.AlbumName
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.PhotoName
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.objects.photo.Photo

interface PhotoStorageA : BaseStorageA {

    fun albums(): Sequence<Album>

    fun allPhotos(): Sequence<Photo>

    fun allPersonNames(): Sequence<PersonName>

    fun photos(album: Album): Sequence<Photo>
    fun photos(personName: PersonName): Sequence<Photo>

    fun photo(albumName: AlbumName, photoName: PhotoName): Photo?

    fun allPersonPhotos(): Sequence<PersonPhoto>
    fun personPhoto(personName: PersonName): PersonPhoto?

    fun addAlbum(albumName: AlbumName): Album
    fun renameAlbum(album: Album, newAlbumName: AlbumName): Album
    fun removeAlbum(album: Album)

    fun addPhoto(album: Album, photoName: PhotoName, imageData: ByteArray) : Photo
    fun renamePhoto(photo: Photo, newPhotoName: PhotoName): Photo
    fun removePhoto(photo: Photo)
    fun movePhoto(photo: Photo, album: Album): Photo

    fun addPersonPhoto(personName: PersonName, imageData: ByteArray) : PersonPhoto
    fun renamePersonPhoto(personPhoto: PersonPhoto, newPersonName: PersonName, renameExisting: Boolean = false): PersonPhoto
    fun removePersonPhoto(personPhoto: PersonPhoto)

    interface Holder {
        val photoStorage: PhotoStorageA
    }


}