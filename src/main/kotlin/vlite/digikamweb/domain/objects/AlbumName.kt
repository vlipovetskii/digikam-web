package vlite.digikamweb.domain.objects

import vlite.core.domain.objects.KHasValidationA
import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import vlite.digikamweb.domain.objects.base.HasPathA
import java.util.regex.Pattern
import kotlin.io.path.Path

@JvmInline
value class AlbumName(override val value: String) : HasPathA, KHasValidationA.NotEmptyString {

    override val path get() = Path(value)

    companion object {

        const val VALID_REGEX = BaseFileStorageA.NAME_VALID_REGEX
        private val VALID_REGEX_PATTERN: Pattern = Pattern.compile(VALID_REGEX)

        const val ATTRIBUTE_NAME = "albumName"

    }

    override val isValid get() = super.isValid && VALID_REGEX_PATTERN.matcher(value).matches()

    /*
        interface Holder {
            val albumName: AlbumName
        }
    */

}
