package vlite.digikamweb.ui.view.admin

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.Route
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.core.ui.onSelected
import vlite.core.ui.removeDrawer
import vlite.digikamweb.domain.services.storage.TenantStorageA
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import vlite.digikamweb.ui.base.view.BaseAppLayoutA
import vlite.digikamweb.ui.base.view.ViewRouteParameter
import vlite.digikamweb.ui.base.view.access.validate
import vlite.digikamweb.ui.view.admin.edittenants.EditTenantsContentA
import java.util.*

/**
 * PRB: Without ending '/' (http://localhost:8080/admin/4321)
 * HttpRequestMethodNotSupportedException: Request method 'GET' is not supported
 * http://localhost:8080/admin/4321/
 * WO: http://localhost:8080/admin/4321/
 * Solution: ?
 */
@Suppress("unused")
@Route(AdminView.ROUTE)
class AdminView(
    override val tenantStorage: TenantStorageA,
    override val editTenantsContent: EditTenantsContentA,
) : BaseAppLayoutA(),
    BeforeEnterObserver,
    ViewRouteParameter.EditAccessCodeValue.Holder,
    EditTenantsContentA.Holder {

    companion object : KLoggerA {

        private val log by lazy { classLogger }

        private const val ROUTE =
            "/admin/${ViewRouteParameter.EditAccessCodeValue.TEMPLATE}"
    }

    private enum class I18n(
        override val en: String,
        override val ru: String,
        override val he: String
    ) : AppI18NProvider.ProvidedValuesA {

        EditTenants(
            en = "Albums",
            ru = "Альбомы",
            he = "אלבומים"
        ),

    }

    override val editAccessCodeRouteParameterValue = ViewRouteParameter.EditAccessCodeValue()

    private var editTenantsTab: Tab? = null

    override fun localeChange(locale: Locale) {
        editTenantsTab?.label = I18n.EditTenants.translation(locale)
    }

    override fun beforeEnter(event: BeforeEnterEvent) {
        editAccessCodeRouteParameterValue.initFrom(event, log)
    }

    override fun initAfterNavigation(event: AfterNavigationEvent) {

        tenantStorage.validate(log) {

            editAccessCodeRouteParameterValue().validate(log, tenantStorage.editAccessCodeFromFile(log)!!) {

                initAfterNavigation(tenantStorage)

            }

        }

    }

    private fun initAfterNavigation(tenantStorage: TenantStorageA) {

        navbar(touchOptimized = true) {
            drawerToggle()

            tabs {

                editTenantsTab = tab {
                    icon(VaadinIcon.EDIT)

                    onSelected {
                        removeDrawer()
                        editTenantsContent.run { populate(tenantStorage) }
                    }
                }

            }
        }

    }

}