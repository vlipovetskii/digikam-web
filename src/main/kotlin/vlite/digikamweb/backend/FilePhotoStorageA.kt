package vlite.digikamweb.backend

import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import java.nio.file.Path

interface FilePhotoStorageA : BaseFileStorageA {

    val peopleDirectoryExists: Boolean

    fun albumDirectories(): Sequence<Path>
    fun allPhotoFiles(): Sequence<Path>
    fun albumPhotoFiles(albumPath: Path): Sequence<Path>

    fun allPersonPhotoFiles(): Sequence<Path>

    fun photoFile(albumName: String, fileNameWithExtension: String): Path?
    fun personPhotoFile(fileNameWithExtension: String): Path?

    fun addDirectory()
    fun renameDirectory(newDirName: String, renameExisting: Boolean = false): FilePhotoStorageA
    fun deleteDirectory()

    fun addAlbumDirectory(dirName: String): Path
    fun renameAlbumDirectory(albumPath: Path, newDirName: String, renameExisting: Boolean = false): Path
    fun removeAlbumDirectory(albumPath: Path)

    fun addPeopleDirectory()
    fun removePeopleDirectory()

    /**
     * [fileNameWithExtension] - <name>.jpg
     */
    fun addPhotoFile(albumPath: Path, fileNameWithExtension: String, imageData: ByteArray): Path
    fun renamePhotoFile(photoFilePath: Path, newFileNameWithExtension: String, renameExisting: Boolean = false): Path
    fun removePhotoFile(photoFilePath: Path)
    fun movePhotoFile(photoFilePath: Path, albumPath: Path): Path

    /**
     * [fileNameWithExtension] - <name>.jpeg
     */
    fun addPersonPhotoFile(fileNameWithExtension: String, imageData: ByteArray): Path
    fun renamePersonPhotoFile(personPhotoFilePath: Path, newFileNameWithExtension: String, renameExisting: Boolean = false): Path
    fun removePersonPhotoFile(personPhotoFilePath: Path)

    /*
        interface Holder {
            val filePhotoStorage : FilePhotoStorageA
        }
    */

}