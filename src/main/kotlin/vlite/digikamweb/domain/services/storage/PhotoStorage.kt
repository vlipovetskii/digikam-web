package vlite.digikamweb.domain.services.storage

import org.slf4j.Logger
import vlite.digikamweb.backend.FilePhotoStorageA
import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.objects.AlbumName
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.PhotoName
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.domain.services.PhotoFactoryA

class PhotoStorage(
    override val fileStorage: FilePhotoStorageA,
    override val photoFactory: PhotoFactoryA,
) : PhotoStorageA,
    BaseStorageA.Implementation<FilePhotoStorageA>,
    PhotoFactoryA.Holder {

    override fun logErrorIfNotExists(log: Logger) {
        fileStorage.logErrorIfNotExists(log)
    }

    override fun albums(): Sequence<Album> {
        return fileStorage
            .albumDirectories()
            .map { albumPath ->
                Album(albumPath)
            }
            .sortedByDescending { it.name.value }
    }

    override fun allPhotos(): Sequence<Photo> {
        return fileStorage
            .allPhotoFiles()
            .map { photoFactory.photo(it) }
    }

    override fun allPersonNames(): Sequence<PersonName> {
        return allPhotos()
            .flatMap {
                it.personNames
            }
            .distinct()
            .sortedBy { it.value }
    }

    override fun photos(album: Album): Sequence<Photo> {
        return fileStorage.albumPhotoFiles(album.path)
            .map { photoFactory.photo(it) }
    }

    override fun photos(personName: PersonName): Sequence<Photo> {
        return allPhotos()
            .filter {
                personName in it.personNames
            }
    }

    override fun photo(albumName: AlbumName, photoName: PhotoName) =
        fileStorage.photoFile(albumName.value, photoName.withExtension)?.let { photoFactory.photo(it) }

    override fun allPersonPhotos(): Sequence<PersonPhoto> {
        return fileStorage
            .allPersonPhotoFiles()
            .map { photoFactory.personPhoto(it) }
    }

    override fun personPhoto(personName: PersonName) =
        fileStorage.personPhotoFile(personName.withExtension)?.let { photoFactory.personPhoto(it) }

    override fun addAlbum(albumName: AlbumName) =
        Album(fileStorage.addAlbumDirectory(albumName.value))

    private fun Album.removePhotosFromPhotoFactory() {
        photos(this).forEach {photo ->
            photoFactory.remove(photo)
        }
    }

    override fun renameAlbum(album: Album, newAlbumName: AlbumName) : Album {
        album.removePhotosFromPhotoFactory()
        return Album(fileStorage.renameAlbumDirectory(album.path, newAlbumName.value))
    }

    override fun removeAlbum(album: Album) {
        album.removePhotosFromPhotoFactory()
        fileStorage.removeAlbumDirectory(album.path)
    }

    override fun addPhoto(album: Album, photoName: PhotoName, imageData: ByteArray) =
        photoFactory.photo(fileStorage.addPhotoFile(album.path, photoName.withExtension, imageData))

    override fun renamePhoto(photo: Photo, newPhotoName: PhotoName): Photo {
        photoFactory.remove(photo)
        return photoFactory.photo(fileStorage.renamePhotoFile(photo.path, newPhotoName.withExtension))
    }

    override fun removePhoto(photo: Photo) {
        photoFactory.remove(photo)
        fileStorage.removePhotoFile(photo.path)
    }

    override fun movePhoto(photo: Photo, album: Album) : Photo {
        photoFactory.remove(photo)
        return photoFactory.photo(fileStorage.movePhotoFile(photo.path, album.path))
    }

    override fun addPersonPhoto(personName: PersonName, imageData: ByteArray) =
        photoFactory.personPhoto(fileStorage.addPersonPhotoFile(personName.withExtension, imageData))

    override fun renamePersonPhoto(personPhoto: PersonPhoto, newPersonName: PersonName, renameExisting: Boolean) : PersonPhoto {
        photoFactory.remove(personPhoto)
        return photoFactory.personPhoto(
            fileStorage.renamePersonPhotoFile(
                personPhoto.path,
                newPersonName.withExtension,
                renameExisting
            )
        )
    }

    override fun removePersonPhoto(personPhoto: PersonPhoto) {
        photoFactory.remove(personPhoto)
        fileStorage.removePersonPhotoFile(personPhoto.path)
    }

}

