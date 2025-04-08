package vlite

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributesting.v10.Routes
import com.github.mvysny.kaributesting.v10.spring.MockSpringServlet
import com.github.mvysny.kaributools.getRouteUrl
import com.github.mvysny.kaributools.navigateTo
import com.vaadin.flow.component.UI
import com.vaadin.flow.router.RouteParam
import com.vaadin.flow.router.RouteParameters
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.getBean
import vlite.core.*
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.services.storage.TenantStorageA
import vlite.digikamweb.ui.base.view.ViewRouteParameter
import vlite.digikamweb.ui.view.admin.AdminView

/**
 * Properly configures the app in the test context, so that the app is properly initialized, and the database is emptied before every test.
 */
abstract class AbstractAppTest : KSpringBootTestA {

    companion object : KLoggerA {
        // since there is no servlet environment, Flow won't auto-detect the @Routes.
        // We need to auto-discover all @Routes
        // and populate the RouteRegistry properly.
        private val routes = Routes().autoDiscoverViews("vlite")

        private val log by lazy { classLogger }

        private val ADMIN_EDIT_ACCESS_CODE = EditAccessCode("1234")
    }

    @BeforeEach
    fun setupVaadin() {
        //MockVaadin.setup(routes)
        log.doOperationWithLogging("MockVaadin.setup") {
            MockVaadin.setup({ UI() }, MockSpringServlet(routes, applicationContext) { UI() })
        }
    }

    @AfterEach
    fun tearDownVaadin() {
        log.doOperationWithLogging("MockVaadin.tearDown") {
            MockVaadin.tearDown()
        }
    }

    // it's a good practice to clear up the db before every test,
    // to start every test with a predefined state.
    @BeforeEach
    fun cleanupStorage() {
        val tenantStorage = beanFactory.getBean<TenantStorageA>()
        tenantStorage.delete(log)
        tenantStorage.initialize(log, ADMIN_EDIT_ACCESS_CODE)
    }

    protected fun testFactory(block: KTestFactoryBuilder.() -> Unit) = kTestFactory(
        beforeEach = { cleanupStorage(); setupVaadin() },
        afterEach = { tearDownVaadin() },
        block= block
    )

    protected fun navigateToAdminView() {
        navigateTo(
            getRouteUrl(
                AdminView::class,
                RouteParameters(
                    RouteParam(ViewRouteParameter.EditAccessCodeValue().routeParameterName, ADMIN_EDIT_ACCESS_CODE.value)
                )
            )
        )
    }

}
