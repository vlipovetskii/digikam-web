package vlite.core.backend

import org.slf4j.Logger
import vlite.core.doOperationWithLogging

data class KProcessExecutionResult(
    val processOutput: List<String>,
    val processErrors: List<String>,
    val processExitCode: Int
)

/**
 * [How to Run a Shell Command in Java] (https://www.baeldung.com/run-shell-command-in-java)
 * [Guide to java.lang.ProcessBuilder API] (https://www.baeldung.com/java-lang-processbuilder-api)
 * [Guide to java.lang.Process API] (https://www.baeldung.com/java-process-api)
 * [How to Invoke External Command From Within Kotlin] (https://www.baeldung.com/kotlin/invoke-external-command)
 *
 * TODO: waitFor(long timeout, TimeUnit unit)
 */
@Suppress("unused")
fun ProcessBuilder.execAndGetResults(
    log: Logger,
    outputStreamToLog: Boolean,
    maskSensitiveData: (String) -> String,
    vararg command: String
): KProcessExecutionResult {
    return log.doOperationWithLogging(maskSensitiveData(command.joinToString(" "))) {
        command(*command)
        with(start()) {
            val processExecutionResult = KProcessExecutionResult(
                processOutput = inputStream.bufferedReader().readLines(),
                processErrors = errorStream.bufferedReader().readLines(),
                processExitCode = waitFor(),
            )

            if (outputStreamToLog) {
                log.info("Process output:\n ${processExecutionResult.processOutput.joinToString(separator = "\n")}")
                if (processExecutionResult.processErrors.isNotEmpty()) log.error(
                    "Process errors:\n ${
                        processExecutionResult.processErrors.joinToString(
                            separator = "\n"
                        )
                    }"
                )
            }
            log.info("Process exit code: $processExecutionResult.processExitCode")

            processExecutionResult
        }
    }
}

@Suppress("unused")
fun ProcessBuilder.execInShellAndGetResults(
    log: Logger,
    outputStreamToLog: Boolean,
    maskSensitiveData: (String) -> String,
    command: String
) =
    execAndGetResults(log, outputStreamToLog, maskSensitiveData, "sh", "-c", command)
