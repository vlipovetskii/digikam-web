package vlite.digikamweb.ui.base.view

import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationObserver
import vlite.core.ui.i18n.KLocaleChangeObserverA
import vlite.digikamweb.domain.services.storage.TenantStorageA
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseAppLayoutA : AppLayout(),
    AfterNavigationObserver,
    KLocaleChangeObserverA,
    TenantStorageA.Holder {

    val appLayoutLocale: Locale get() = locale

    private val addToLayoutIsInvoked = AtomicBoolean()

    abstract fun initAfterNavigation(event: AfterNavigationEvent)

    /**
     * PRB: beforeEnter is invoked after init.
     * So, properties (like tenantName), which are initialized in beforeEnter,
     * are not initialized when init is invoked.
     * WO: Move initialization code from init to [initAfterNavigation].
     */
    override fun afterNavigation(event: AfterNavigationEvent) {
        if (!addToLayoutIsInvoked.getAndSet(true)) {
            initAfterNavigation(event)
        }

        /**
         * private lateinit var albumsTab: Tab
         * ...
         * PRB: [localeChange] is invoked BEFORE [afterNavigation].
         * So, lateinit properties like albumsTab, ... are not initialized yet.
         * WO:
         * private var albumsTab: Tab? = null
         * albumsTab?.label = I18n.Albums.run { localized }
         * and
         * manually invoke [localeChange] in [afterNavigation],
         * AFTER localized components have been initialized
         * SEE: [Using Localization without LocaleChangeObserver](https://vaadin.com/docs/latest/flow/advanced/i18n-localization#using-localization-without-localechangeobserver)
         */
        localeChange(locale)

    }

}