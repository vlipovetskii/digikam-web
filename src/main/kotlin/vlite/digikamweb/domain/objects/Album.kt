package vlite.digikamweb.domain.objects

import vlite.digikamweb.domain.objects.base.HasPathA
import java.nio.file.Path
import kotlin.io.path.name

data class Album(override val path: Path) : HasPathA {

    val name get() = AlbumName(path.name)

    interface Holder {
        val album : Album
    }
}