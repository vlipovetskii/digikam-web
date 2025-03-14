package vlite.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

/**
 * http://www.baeldung.com/kotlin-logging
 */
interface KLoggerA {
    @Suppress("unused")
    interface Holder {
        val log: Logger
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T : KLoggerA> getClassForLogging(javaClass: Class<T>): Class<*> = javaClass.enclosingClass?.takeIf {
    it.kotlin.companionObject?.java == javaClass
} ?: javaClass

inline val <T : KLoggerA> T.classLogger: Logger
    get() = LoggerFactory.getLogger(getClassForLogging(javaClass))


/**
 * If exception propagation is not required:
 * try { log.doCommandWithLogging(...) } catch (e: Exception) { }
 * In some cases, [doOperationBlock] has already invoked [stackTraceToString]. Pass [isStackTraceToString] false to suppress duplication of [stackTraceToString]
 */
@Suppress("unused")
inline fun <R> Logger.doOperationWithLogging(
    operationTag: String,
    isLoggingEnabled: Boolean = true,
    isStackTraceToString: Boolean = true,
    doOperationBlock: (commandText: String) -> R
) =
    (if (isLoggingEnabled) info("'$operationTag' started") else Unit).let {
        try {
            doOperationBlock(operationTag).also {
                if (isLoggingEnabled) info("'$operationTag' succeded")
            }
        } catch (e: Exception) {
            error("'$operationTag' failed")
            if (isStackTraceToString) error(e.stackTraceToString())
            // propagate always
            throw e
        }
    }

