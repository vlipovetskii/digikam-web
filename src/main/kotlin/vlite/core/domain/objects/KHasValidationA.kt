package vlite.core.domain.objects

interface KHasValidationA {
    val isValid: Boolean

    interface NotEmptyString : KHasValidationA {
        val value: String

        override val isValid get() = value.run { isNotEmpty() && isNotBlank() }
    }

}