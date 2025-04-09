package vlite.core.unit

import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.core.KSpringBootTestA
import vlite.digikamweb.AppTestBeansInitializer
import vlite.digikamweb.integration.AdminViewTests
import vlite.digikamweb.unit.FileTenantStorageTest
import vlite.digikamweb.unit.RegexTests

open class AllCoreUnitTests {

	@Nested inner class KSpringEnvUtilsTestsNested : KSpringEnvUtilsTest()

}
