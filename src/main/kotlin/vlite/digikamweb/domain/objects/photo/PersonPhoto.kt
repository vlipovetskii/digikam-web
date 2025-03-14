package vlite.digikamweb.domain.objects.photo

import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.TenantName
import vlite.digikamweb.domain.objects.base.BasePhotoA
import java.nio.file.Path
import kotlin.io.path.name

data class PersonPhoto(
    override val path: Path,
) : BasePhotoA<PersonName>() {

    companion object {
        //const val SIZE = 2 // em

        const val MAX_SIZE = 1024 * 1024

    }

    override val name get() = PersonName(strNameWithoutExtension)

    // tenantName/.people/name.jpg
    val tenantName get() = TenantName(path.parent.parent.name)

    interface Holder {
            val personPhoto: PersonPhoto
        }

}
