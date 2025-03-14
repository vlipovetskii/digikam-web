package vlite.digikamweb.domain.objects

import vlite.core.domain.objects.KHasValidationA
import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import vlite.digikamweb.domain.objects.base.BasePhotoNameA
import java.util.regex.Pattern

@JvmInline
value class PersonName(override val value: String) : BasePhotoNameA, KHasValidationA.NotEmptyString {

    companion object {

        const val ATTRIBUTE_NAME = "personName"

        private const val VALID_REGEX = BaseFileStorageA.NAME_VALID_REGEX
        private val VALID_REGEX_PATTERN: Pattern = Pattern.compile(VALID_REGEX)

    }

    override val isValid get() = super.isValid && VALID_REGEX_PATTERN.matcher(value).matches()

/*
    init {
        PhotoName.requireWithoutExtension(value)
    }
*/

    interface Holder {
        val personName: PersonName
    }

}
