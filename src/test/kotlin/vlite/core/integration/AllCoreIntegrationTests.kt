package vlite.core.integration

import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.core.CoreTestBeansInitializer
import vlite.core.KSpringBootTestA

@SpringBootTest
@ContextConfiguration(initializers = [CoreTestBeansInitializer::class])
class AllCoreIntegrationTests(
	@Autowired override val beanFactory: BeanFactory,
	@Autowired override val applicationContext: ApplicationContext,
) : KSpringBootTestA {


}
