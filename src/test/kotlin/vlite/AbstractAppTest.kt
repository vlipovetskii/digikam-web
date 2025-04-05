package vlite

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributesting.v10.Routes
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.digikamweb.domain.services.storage.TenantStorageA
import vlite.digikamweb.domain.services.storage.clean

/**
 * Properly configures the app in the test context, so that the app is properly initialized, and the database is emptied before every test.
 */
abstract class AbstractAppTest {

    abstract val beanFactory: BeanFactory
    abstract val applicationContext : ApplicationContext

    companion object : KLoggerA {
        // since there is no servlet environment, Flow won't auto-detect the @Routes.
        // We need to auto-discover all @Routes
        // and populate the RouteRegistry properly.
        private val routes = Routes().autoDiscoverViews("com.vaadin.starter")

        private val log by lazy { classLogger }
    }

    @BeforeEach
    fun setupVaadin() {
        MockVaadin.setup(routes)
    }

    @AfterEach
    fun tearDownVaadin() {
        MockVaadin.tearDown()
    }

    // it's a good practice to clear up the db before every test,
    // to start every test with a predefined state.
    @BeforeEach
//    @AfterEach
    fun cleanupDb() {
        // CategoryService.deleteAll(); ReviewService.deleteAll()
        val tenantStorage = beanFactory.getBean<TenantStorageA>()
        tenantStorage.clean(log)
    }
}
