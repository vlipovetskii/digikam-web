@file:Suppress("unused")

package vlite.core

import org.slf4j.Logger
import org.springframework.core.env.Environment

inline fun <reified T : Any> Environment.getRequiredProperty(
    key: String,
    log: Logger,
    logValue: Boolean = true,
    maskValue: String = "***"
): T {
    try {
        val value = getRequiredProperty(key, T::class.java)

        if (T::class == String::class && (value as String).run { isBlank() || isEmpty() }) {
            throw IllegalStateException("Required property '$key' is empty")
        }

        log.info("getRequiredProperty('$key') -> '${value.takeIf { logValue } ?: maskValue}'")
        return value
    } catch (e: Exception) {
        log.error(e.stackTraceToString())
        throw e
    }
}

inline fun <reified T : Any> Environment.getProperty(
    key: String,
    log: Logger,
    logValue: Boolean = true,
    maskValue: String = "***"
): T? {
    try {
        val value: T? = getProperty(key, T::class.java)
        log.info("getProperty('$key') -> '${value.takeIf { logValue } ?: maskValue}'")
        return value
    } catch (e: Exception) {
        log.error(e.stackTraceToString())
        throw e
    }
}

fun Environment.getOptionalStringProperty(key: String, log: Logger, logValue: Boolean = true) =
    getProperty<String>(key, log, logValue)?.takeIf { it.isNotEmpty() && it.isNotBlank() }