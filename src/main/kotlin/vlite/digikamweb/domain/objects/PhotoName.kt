package vlite.digikamweb.domain.objects

import vlite.core.domain.objects.KHasValidationA
import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import vlite.digikamweb.domain.objects.base.BasePhotoNameA
import java.util.regex.Pattern
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension

@JvmInline
value class PhotoName(override val value: String) : BasePhotoNameA, KHasValidationA.NotEmptyString {

    companion object {

        const val ATTRIBUTE_NAME = "photoName"

        private const val VALID_REGEX = BaseFileStorageA.NAME_VALID_REGEX
        private val VALID_REGEX_PATTERN: Pattern = Pattern.compile(VALID_REGEX)

        const val EXT = "jpg"

        fun fromFileNameWithExtension(fileName: String) =
            PhotoName(Path(fileName).nameWithoutExtension)

        fun requireWithoutExtension(value: String) {
            require(!value.endsWith(".$EXT", ignoreCase = true)) { "`$value` ends with extension `.$EXT`" }
        }

    }

    override val isValid get() = super.isValid && VALID_REGEX_PATTERN.matcher(value).matches()

    init {
        requireWithoutExtension(value)
    }

    /*
        interface Holder {
            val photoName: PhotoName
        }
    */

}
