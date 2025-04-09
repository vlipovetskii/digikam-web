package vlite.digikamweb.integration

import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.core.KSpringBootTestA
import vlite.digikamweb.AppTestBeansInitializer

@SpringBootTest
@ContextConfiguration(initializers = [AppTestBeansInitializer::class])
class AllAppIntegrationTests(
	@Autowired override val beanFactory: BeanFactory,
	@Autowired override val applicationContext: ApplicationContext,
) : KSpringBootTestA {

	@Nested inner class AdminViewTestsNested : AdminViewTests(beanFactory, applicationContext)

}
