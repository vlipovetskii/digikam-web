package vlite.digikamweb.domain.objects

import vlite.core.domain.objects.KHasValidationA
import java.util.regex.Pattern

@JvmInline
value class EditAccessCode(override val value: String) : KHasValidationA.NotEmptyString {

    companion object {

        const val ATTRIBUTE_NAME = "editAccessCode"

        private const val MINIMUM_LENGTH = 4
        private const val VALID_REGEX = "^[a-zA-Z0-9\\-]{$MINIMUM_LENGTH,}$"
        private val VALID_REGEX_PATTERN: Pattern = Pattern.compile(VALID_REGEX)

    }

    override val isValid get() = super.isValid && VALID_REGEX_PATTERN.matcher(value).matches()

    interface NullableHolder {
        val editAccessCode: EditAccessCode?
    }

}
