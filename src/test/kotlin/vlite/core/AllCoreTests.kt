package vlite.core

import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.core.unit.AllCoreUnitTests

@SpringBootTest
@ContextConfiguration(initializers = [CoreTestBeansInitializer::class])
class AllCoreTests(
	@Autowired override val beanFactory: BeanFactory,
	@Autowired override val applicationContext: ApplicationContext,
) : KSpringBootTestA {

	/**
	 * [JUnit 5 @Nested Test Classes](https://www.baeldung.com/junit-5-nested-test-classes)
	 * [2.13. Nested Tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-nested)
	 */
	@Nested inner class AllCoreUnitTestsNested : AllCoreUnitTests()
	// @Nested inner class AllCoreIntegrationTestsNested : AllCoreIntegrationTests(beanFactory, applicationContext)

}
