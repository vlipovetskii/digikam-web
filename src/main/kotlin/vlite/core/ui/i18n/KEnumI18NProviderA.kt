package vlite.core.ui.i18n

import com.vaadin.flow.i18n.I18NProvider
import java.util.*
import kotlin.enums.EnumEntries

/**
 * [Localization](https://vaadin.com/docs/latest/flow/advanced/i18n-localization)
 * [SimpleI18NProvider.java](https://github.com/vaadin/flow-spring-examples/blob/v24/src/main/java/org/vaadin/spring/tutorial/SimpleI18NProvider.java)
 */
abstract class KEnumI18NProviderA<TProvidedLocales>(providedLocales: EnumEntries<TProvidedLocales>) : I18NProvider
where TProvidedLocales: Enum<TProvidedLocales>, TProvidedLocales: KEnumI18NProviderA.ProvidedLocalesA
{

    private val providedLocales: List<Locale> = providedLocales.toList().map { it.locale }

    interface ProvidedLocalesA {
        val locale: Locale
    }

    /**
     * [Locale Selection for New Session](https://vaadin.com/docs/latest/flow/advanced/i18n-localization#locale-selection-for-new-session)
     * The initial locale is determined by matching the locales provided by the I18NProvider
     * against the Accept-Language header in the initial response from the client.
     *
     * [SimpleI18NProvider.java](https://github.com/vaadin/flow-spring-examples/blob/v24/src/main/java/org/vaadin/spring/tutorial/SimpleI18NProvider.java)
     */
    override fun getProvidedLocales() = providedLocales

    override fun getTranslation(key: String, locale: Locale, vararg params: Any): String? {
        error("getTranslation mustn't be used")
    }

}
