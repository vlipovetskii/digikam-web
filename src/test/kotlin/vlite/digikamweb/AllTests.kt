package vlite.digikamweb

import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.core.KSpringBootTestA
import vlite.digikamweb.integration.AdminViewTests
import vlite.digikamweb.unit.RegexTests

/**
 * TODO Cover application code with tests
 */
@SpringBootTest
@ContextConfiguration(initializers = [AppTestBeansInitializer::class])
class AllTests(
	@Autowired override val beanFactory: BeanFactory,
	@Autowired override val applicationContext: ApplicationContext,
) : KSpringBootTestA {

	/**
	 * [JUnit 5 @Nested Test Classes](https://www.baeldung.com/junit-5-nested-test-classes)
	 * [2.13. Nested Tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-nested)
	 */
	@Nested inner class RegexTestsNested : RegexTests()
	@Nested inner class AdminViewTestsNested : AdminViewTests(beanFactory, applicationContext)

}
