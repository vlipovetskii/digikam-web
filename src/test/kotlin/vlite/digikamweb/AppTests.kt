package vlite.digikamweb

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import vlite.AbstractAppTest

/**
 * TODO Cover application code with tests
 */
@SpringBootTest
class AppTests(
	@Autowired override val beanFactory: BeanFactory,
	@Autowired override val applicationContext: ApplicationContext,
) : AbstractAppTest() {

	@Test
	fun contextLoads() {
	}

}
