package vlite.digikamweb.integration

import com.github.mvysny.kaributesting.v10.expectView
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.AbstractAppTest
import vlite.core.kTestFactory
import vlite.digikamweb.AppTestBeansInitializer
import vlite.digikamweb.ui.view.admin.AdminView

@SpringBootTest
@ContextConfiguration(initializers = [AppTestBeansInitializer::class])
@Suppress("ClassName")
class IT_00_Tenants(
    @Autowired override val beanFactory: BeanFactory,
    @Autowired override val applicationContext: ApplicationContext,
) : AbstractAppTest() {

    @TestFactory
    fun testFactory() = kTestFactory {

        "Add tenant-1" {

            navigateToAdminView()
            expectView<AdminView>()

        }

        "test2" {

        }

    }

}
