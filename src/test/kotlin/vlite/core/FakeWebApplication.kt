package vlite.core

import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * PRB: java.lang.IllegalStateException:
 * Unable to find a @SpringBootConfiguration by searching packages upwards from the test.
 * You can use @ContextConfiguration, @SpringBootTest(classes=...) or
 * other Spring Test supported mechanisms to explicitly declare the configuration classes to load.
 * Classes annotated with @TestConfiguration are not considered.
 *
 * Spring Boot's test context loader can't find your application’s main configuration class
 * (annotated with @SpringBootApplication or @SpringBootConfiguration) when running the test.
 *
 * WO: Put class annotated with @SpringBootApplication to the root package with test classes
 */
@SpringBootApplication
class TestCoreApplication
