package vlite.digikamweb.ui.base.view

import vlite.core.domain.services.toStringWithFormat
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import java.util.*

private enum class I18n(
    override val en: String,
    override val ru: String,
    override val he: String
) : AppI18NProvider.ProvidedValuesA {

    DateTimeOriginalMissing(
        en = "Original date time is missing",
        ru = "Исходная дата и время отсутствуют",
        he = "התאריך המקורי חסר"
    ),

}

fun Photo.dateTimeOriginal(locale: Locale) =
    dateTimeOriginal?.toStringWithFormat() ?: I18n.DateTimeOriginalMissing.translation(locale)

val Photo.personNamesString get() =
    personNames.joinToString { it.value }

