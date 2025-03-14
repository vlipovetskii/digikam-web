package vlite.digikamweb.domain.objects.photo

import vlite.core.backend.imageMetadata
import vlite.core.domain.services.standardEqualsOf
import vlite.core.domain.services.standardHashOf
import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.PhotoName
import vlite.digikamweb.domain.objects.TenantName
import vlite.digikamweb.domain.objects.base.BasePhotoA
import java.nio.file.Path
import kotlin.io.path.name

data class Photo(
    override val path: Path,
) : BasePhotoA.WithMetadata<PhotoName>(path.imageMetadata) {

    companion object {
        const val MAX_SIZE = 1024*1024
    }

    override fun equals(other: Any?): Boolean {
        return standardEqualsOf(
            other,
            { path == it.path },
        )
    }

    override fun hashCode() = standardHashOf(
        path,
    )

    override val name get() = PhotoName(strNameWithoutExtension)

    // tenantName/albumName/name.jpg
    val albumName get() = TenantName(path.parent.name)
    val tenantName get() = TenantName(path.parent.parent.name)

    fun withRefreshedMetadata() = Photo(path)

    val album get() = Album(path.parent)

    // MP:RegionInfo/MPRI:Regions[1]/MPReg:PersonDisplayName
    val personNames: Set<PersonName> = xmpProperties
        ?.filter { it.key.endsWith(":PersonDisplayName") }
        ?.values
        ?.map { PersonName(it) }
        ?.toSet() ?: setOf()

    /*
                imageBytes?.imageMetadata?.firstDirectoryOfType<ExifSubIFDDirectory>()?.dateOriginal?.let {
                    PhotoDateTime(it.toLocalDateTime())
                } ?: PhotoDateTime.default,
     */

    interface Holder {
        val photo: Photo
    }

}
