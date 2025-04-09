package vlite.core.unit

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.slf4j.Logger
import org.springframework.core.env.Environment
import vlite.core.getRequiredProperty
import java.util.stream.Stream
import kotlin.test.assertFailsWith
import kotlin.test.expect

open class KSpringEnvUtilsTest {

    @TestFactory
    fun test_getRequiredProperty_Factory(): Stream<DynamicTest> {
        val testCases = listOf(
            Triple("validValue", "someValue", true),
            Triple("blankValue", "   ", false),
            Triple("emptyValue", "", false),
        )

        return testCases.map { (key, propValue, shouldPass) ->
            DynamicTest.dynamicTest("Key '$key' with value '$propValue' should ${if (shouldPass) "succeed" else "fail"}") {
                val env = mockk<Environment>()
                val log = mockk<Logger>(relaxed = true) // relaxed to allow log.info/log.error without setup

                every { env.getRequiredProperty(key, String::class.java) } returns propValue

                if (shouldPass) {
                    val result: String = env.getRequiredProperty(key, log)
                    expect(propValue) { result }
                    verify {
                        env.getRequiredProperty(key, String::class.java)
                        log.info("getRequiredProperty('$key') -> '$propValue'")
                    }
                } else {
                    assertFailsWith<IllegalStateException> {
                        env.getRequiredProperty<String>(key, log)
                    }
                    verify {
                        env.getRequiredProperty(key, String::class.java)
                        log.error(match { it.contains("IllegalStateException") })
                    }
                }

                confirmVerified(env, log)
            }
        }.stream()
    }

}