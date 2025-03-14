package vlite.digikamweb.domain.objects

import vlite.core.domain.objects.KHasValidationA
import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import java.util.regex.Pattern

@JvmInline
value class TenantName(override val value: String) : KHasValidationA.NotEmptyString {

    companion object {

        const val ATTRIBUTE_NAME = "tenantName"

        private const val VALID_REGEX = BaseFileStorageA.NAME_VALID_REGEX
        private val VALID_REGEX_PATTERN: Pattern = Pattern.compile(VALID_REGEX)

    }

    override val isValid get() = super.isValid && VALID_REGEX_PATTERN.matcher(value).matches()

    interface Holder {
        val tenantName: TenantName
    }

}
