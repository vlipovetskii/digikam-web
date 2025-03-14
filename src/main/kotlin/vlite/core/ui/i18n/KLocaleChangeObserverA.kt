package vlite.core.ui.i18n

import com.vaadin.flow.i18n.LocaleChangeEvent
import com.vaadin.flow.i18n.LocaleChangeObserver
import java.util.*

/**
 * [Using Localization in Application](https://vaadin.com/docs/latest/flow/advanced/i18n-localization#using-localization-in-application)
 * ```
 * To make this simple, the application classes that control the captions and texts that are localized,
 * can implement LocaleChangeObserver to receive events related to locale change.
 * This observer is also notified on navigation when the component is attached, but before onAttach() is called.
 * Any URL parameters from the navigation are set so that they can be used to determine the state.
 * ```
 */
interface KLocaleChangeObserverA : LocaleChangeObserver {

    override fun localeChange(localeChangeEvent: LocaleChangeEvent) {
        localeChange(localeChangeEvent.locale)
    }

    fun localeChange(locale: Locale)

}