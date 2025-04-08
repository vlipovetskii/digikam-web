package vlite.digikamweb

import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.digikamweb.integration.IT_00_Tenants

/**
 * TODO Cover application code with tests
 */
@SpringBootTest
@ContextConfiguration(initializers = [AppTestBeansInitializer::class])
class AllTests(
	@Autowired val beanFactory: BeanFactory,
	@Autowired val applicationContext: ApplicationContext,
) {

	@Suppress("ClassName")
	@Nested inner class IT_00_Tenants1 : IT_00_Tenants(beanFactory, applicationContext)

}
