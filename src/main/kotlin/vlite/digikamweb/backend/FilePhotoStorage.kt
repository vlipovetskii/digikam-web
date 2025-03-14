package vlite.digikamweb.backend

import org.slf4j.Logger
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.*

class FilePhotoStorage(
    override val homePath: Path
) : FilePhotoStorageA,
    BaseFileStorageA.Implementation {

    companion object : KLoggerA {
        private val log by lazy { classLogger }
    }

    override fun logErrorIfNotExists(log: Logger) {
        log.error("FilePhotoStorage(`$homePath`) does not exist")
    }

    private val peoplePath: Path = homePath.resolve(".people")

    override val peopleDirectoryExists get() = peoplePath.exists()

    private fun Path.validAlbumPath() {
        require(parent == homePath) { "`$parent` != `$homePath`" }
    }

    private fun Path.validPhotoFilePath() {
        require(parent.parent == homePath) { "`${parent.parent}` != `$homePath`" }
    }

    private fun Path.validPersonPhotoFilePath() {
        require(parent == peoplePath) { "`${parent}` != `$peoplePath`" }
    }

    private fun Path.existsOrNull() : Path? {
        if (exists()) {
            return this
        } else {
            log.error("`$this` not found")
            return null
        }
    }

    override fun albumDirectories(): Sequence<Path> {
        return homePath
            .listDirectoryEntries()
            .asSequence()
            .filter { it.isDirectory() && !it.isHidden() }
    }

    override fun allPhotoFiles(): Sequence<Path> {
        return albumDirectories()
            .flatMap { albumPath ->
                albumPhotoFiles(albumPath)
            }
    }

    override fun albumPhotoFiles(albumPath: Path): Sequence<Path> {
        return albumPath
            .listDirectoryEntries()
            .asSequence()
            .filter { it.isRegularFile() && !it.isHidden() }
    }

    override fun allPersonPhotoFiles(): Sequence<Path> {
        return peoplePath
            .listDirectoryEntries()
            .asSequence()
            .filter { it.isRegularFile() && !it.isHidden() }
    }

    override fun photoFile(albumName: String, fileNameWithExtension: String) =
        homePath.resolve(albumName).resolve(fileNameWithExtension).existsOrNull()

    override fun personPhotoFile(fileNameWithExtension: String) =
        peoplePath.resolve(fileNameWithExtension).existsOrNull()

    override fun addDirectory() {
        homePath.createDirectory()
    }

    override fun renameDirectory(newDirName: String, renameExisting: Boolean): FilePhotoStorageA =
        FilePhotoStorage(homePath.rename(newDirName, renameExisting))

    override fun deleteDirectory() {
        homePath.deleteDirectory()
    }

    override fun addAlbumDirectory(dirName: String): Path =
        homePath.resolve(dirName).also {
            it.createDirectory()
        }

    override fun renameAlbumDirectory(albumPath: Path, newDirName: String, renameExisting: Boolean): Path {
        with(albumPath) {
            validAlbumPath()
            return rename(newDirName, renameExisting)
        }
    }

    override fun removeAlbumDirectory(albumPath: Path) {
        with(albumPath) {
            validAlbumPath()
            deleteDirectory()
        }
    }

    override fun addPeopleDirectory() {
        peoplePath.createDirectory()
    }

    override fun removePeopleDirectory() {
        peoplePath.deleteDirectory()
    }

    override fun addPhotoFile(albumPath: Path, fileNameWithExtension: String, imageData: ByteArray) =
        imageData.writeTo(albumPath, fileNameWithExtension)

    override fun renamePhotoFile(photoFilePath: Path, newFileNameWithExtension: String, renameExisting: Boolean): Path {
        with(photoFilePath) {
            validPhotoFilePath()
            return rename(newFileNameWithExtension, renameExisting)
        }
    }

    override fun removePhotoFile(photoFilePath: Path) {
        with(photoFilePath) {
            validPhotoFilePath()
            deleteFile()
        }
    }

    override fun movePhotoFile(photoFilePath: Path, albumPath: Path): Path {
        photoFilePath.validPhotoFilePath()
        albumPath.validAlbumPath()
        return albumPath.resolve(photoFilePath.name).also { newPhotoFilePath ->
            Files.move(photoFilePath, newPhotoFilePath)
        }
    }

    override fun addPersonPhotoFile(fileNameWithExtension: String, imageData: ByteArray) =
        imageData.writeTo(peoplePath, fileNameWithExtension)

    override fun renamePersonPhotoFile(
        personPhotoFilePath: Path,
        newFileNameWithExtension: String,
        renameExisting: Boolean
    ): Path {
        with(personPhotoFilePath) {
            validPersonPhotoFilePath()
            return rename(newFileNameWithExtension, renameExisting)
        }
    }

    override fun removePersonPhotoFile(personPhotoFilePath: Path) {
        with(personPhotoFilePath) {
            validPersonPhotoFilePath()
            deleteFile()
        }
    }

}