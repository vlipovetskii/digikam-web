package vlite.digikamweb.domain.objects

import vlite.digikamweb.domain.objects.base.HasPathA
import java.nio.file.Path
import kotlin.io.path.name

data class Tenant(override val path: Path) : HasPathA {

    data class ChildrenCount(
        val albums: Int,
        val photos: Int,
        val personPhotos: Int
    ) {
        val isNotZero get()= albums > 0 || photos > 0 || personPhotos > 0
    }

    val name get() = TenantName(path.name)

    interface Holder {
        val tenant : Tenant
    }
}