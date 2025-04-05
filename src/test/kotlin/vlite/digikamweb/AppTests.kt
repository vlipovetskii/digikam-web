package vlite.digikamweb

import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.AbstractAppTest
import vlite.core.kTestFactory

/**
 * TODO Cover application code with tests
 */
@SpringBootTest
@ContextConfiguration(initializers = [AppTestBeansInitializer::class])
class AppTests(
	@Autowired override val beanFactory: BeanFactory,
	@Autowired override val applicationContext: ApplicationContext,
) : AbstractAppTest() {

	@TestFactory
	fun testFactory() = kTestFactory {

		testGroup("Test group 1") {

			"test1" {

			}

		}

		"test1" {

		}

		"test2" {

		}

	}

}
