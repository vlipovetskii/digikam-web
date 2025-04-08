package vlite.digikamweb

import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.AbstractAppTest
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.core.kTestFactory
import vlite.digikamweb.integration.IT_00_Tenants

/**
 * TODO Cover application code with tests
 */
@SpringBootTest
@ContextConfiguration(initializers = [AppTestBeansInitializer::class])
class AppTests(
	@Autowired override val beanFactory: BeanFactory,
	@Autowired override val applicationContext: ApplicationContext,
) : AbstractAppTest() {

	companion object : KLoggerA {
		private val log by lazy { classLogger }
	}

	@TestFactory
	fun testFactory() = kTestFactory {

		+IT_00_Tenants(beanFactory, applicationContext).testFactory()

	}

}
