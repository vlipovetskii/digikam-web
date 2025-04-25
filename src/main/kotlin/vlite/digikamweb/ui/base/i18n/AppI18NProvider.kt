package vlite.digikamweb.ui.base.i18n

import vlite.core.domain.objects.KProvidedTranslationsA
import vlite.core.ui.i18n.KEnumI18NProviderA
import java.util.*

class AppI18NProvider : KEnumI18NProviderA<AppI18NProvider.ProvidedLocales>(ProvidedLocales.entries) {

    enum class ProvidedLocales(override val locale: Locale) : ProvidedLocalesA {
        EN(Locale.ENGLISH),
        RU(Locale.of("ru")),
        HE(Locale.of("he")),
    }

    interface ProvidedValuesA : KProvidedTranslationsA {
        val en: String
        val ru: String
        val he: String

        override fun translation(locale: Locale) =
            when(locale) {
                ProvidedLocales.EN.locale -> en
                ProvidedLocales.RU.locale -> ru
                ProvidedLocales.HE.locale -> he
                else -> en
            }

    }

}