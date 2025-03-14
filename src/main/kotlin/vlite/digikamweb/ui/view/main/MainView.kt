package vlite.digikamweb.ui.view.main

import com.github.mvysny.karibudsl.v10.drawerToggle
import com.github.mvysny.karibudsl.v10.navbar
import com.github.mvysny.karibudsl.v10.tab
import com.github.mvysny.karibudsl.v10.tabs
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.router.*
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.core.ui.onSelected
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.TenantName
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.domain.services.storage.TenantStorageA
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import vlite.digikamweb.ui.base.view.BaseAppLayoutA
import vlite.digikamweb.ui.base.view.ViewRouteParameter
import vlite.digikamweb.ui.base.view.access.validate
import vlite.digikamweb.ui.view.main.MainView.Companion.ROUTE
import vlite.digikamweb.ui.view.main.albums.AlbumDrawerA
import vlite.digikamweb.ui.view.main.people.PeopleDrawerA
import java.util.*

@Suppress("unused")
@Route(ROUTE)
class MainView(
    override val tenantStorage: TenantStorageA,
    override val albumDrawer: AlbumDrawerA,
    override val peopleDrawer: PeopleDrawerA,
) : BaseAppLayoutA(),
    BeforeEnterObserver,
    ViewRouteParameter.TenantNameValue.Holder,
    AlbumDrawerA.Holder,
    PeopleDrawerA.Holder {

    companion object : KLoggerA {

        private val log by lazy { classLogger }

        /**
         * [Route Parameters Matching a Regular Expression] https://vaadin.com/docs/latest/flow/routing/additional-guides/route-templates
         * ```
         * @Route("user/:userID?([0-9]{1,9})/edit")
         * ```
         */
        private const val ROUTE = "/${ViewRouteParameter.TenantNameValue.TEMPLATE}"

        fun navigateToMe(tenantName: TenantName, editAccessCode: EditAccessCode) {
            UI.getCurrent().navigate(
                MainView::class.java,
                RouteParam(TenantName.ATTRIBUTE_NAME, tenantName.value),
            )
        }

    }

    private enum class I18n(
        override val en: String,
        override val ru: String,
        override val he: String
    ) : AppI18NProvider.ProvidedValuesA {

        Albums(
            en = "Albums",
            ru = "Альбомы",
            he = "אלבומים"
        ),

        People(
            en = "People",
            ru = "Люди",
            he = "אנשים"
        ),

    }

    override fun localeChange(locale: Locale) {
        albumsTab?.label = I18n.Albums.translation(locale)
        peopleTab?.label = I18n.People.translation(locale)
    }

    override val tenantNameRouteParameterValue = ViewRouteParameter.TenantNameValue()

    private var albumsTab: Tab? = null
    private var peopleTab: Tab? = null

    /*
        init {
            val photoStorage = tenantStorage.photoStorage(tenantName)

            navbar(touchOptimized = true) {
                ...
            }
            ...

        }
    */

    override fun beforeEnter(event: BeforeEnterEvent) {
        tenantNameRouteParameterValue.initFrom(event, log)
    }

    override fun initAfterNavigation(event: AfterNavigationEvent) {
        tenantStorage.validate(log) {

            val photoStorage = tenantStorage.photoStorage(tenantNameRouteParameterValue())

            photoStorage.validate(log) {
                initAfterNavigation(photoStorage)
            }
        }

    }

    private fun initAfterNavigation(photoStorage: PhotoStorageA) {

        navbar(touchOptimized = true) {
            drawerToggle()

            /*
                        onSelectedTabs {

                            albumsTab = tab(icon = VaadinIcon.FILE_TREE) {

                                onSelected {
                                    albumDrawer.run { populate(photoStorage) }
                                }
                            }

                            peopleTab = tab(icon = VaadinIcon.USERS) {

                                onSelected {
                                    peopleDrawer.run { populate(photoStorage) }
                                }

                            }

                        }
            */

            tabs {

                albumsTab = tab(icon = VaadinIcon.FILE_TREE) {

                    onSelected {
                        albumDrawer.run { populate(photoStorage) }
                    }

                }

                peopleTab = tab(icon = VaadinIcon.USERS) {

                    onSelected {
                        peopleDrawer.run { populate(photoStorage) }
                    }

                }

            }


        }
    }

}