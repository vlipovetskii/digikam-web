package vlite.digikamweb.domain.objects.base

import vlite.digikamweb.domain.objects.PhotoName
import kotlin.io.path.Path

interface BasePhotoNameA : HasPathA {

    val value: String

    val withExtension get() = "$value.${PhotoName.EXT}"
    override val path get() = Path(withExtension)

}