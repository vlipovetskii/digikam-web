package vlite.digikamweb.ui.base.i18n

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.component.upload.UploadI18N
import java.util.*

private sealed class I18n {

    enum class AddFiles(
        override val en: String,
        override val ru: String,
        override val he: String
    ) : AppI18NProvider.ProvidedValuesA {

        One(
            en = "Upload File...",
            ru = "Загрузить файл…",
            he = "העלה קובץ…"
        ),

        Many(
            en = "Upload Files...",
            ru = "Загрузить файлы…",
            he = "העלה קבצים..."
        ),

    }

    enum class DropFiles(
        override val en: String,
        override val ru: String,
        override val he: String
    ) : AppI18NProvider.ProvidedValuesA {

        One(
            en = "Drop file here",
            ru = "Перетащите файл сюда",
            he = "שחרר את הקובץ כאן"
        ),

        Many(
            en = "Drop files here",
            ru = "Перетащите файлы сюда",
            he = "זרוק קבצים כאן"
        ),

    }

    enum class Error(
        override val en: String,
        override val ru: String,
        override val he: String
    ) : AppI18NProvider.ProvidedValuesA {

        TooManyFiles(
            en = "Too many files",
            ru = "Ошибка доступа",
            he = "יותר מדי קבצים"
        ),
        FileIsTooBig(
            en = "File is too Big",
            ru = "Ошибка доступа",
            he = "הקובץ גדול מדי"
        ),
        IncorrectFileType(
            en = "Incorrect file type",
            ru = "Ошибка доступа",
            he = "סוג קובץ שגוי"
        ),

    }

    sealed class Uploading {

        enum class Status(
            override val en: String,
            override val ru: String,
            override val he: String
        ) : AppI18NProvider.ProvidedValuesA {

            Connecting(
                en = "Connecting",
                ru = "Соединение",
                he = "מְקַשֵׁר"
            ),

            Stalled(
                en = "Stalled",
                ru = "Остановился",
                he = "אָבוּס"
            ),

            Processing(
                en = "Processing",
                ru = "Обработка",
                he = "מעבד"
            ),

            Held(
                en = "Held",
                ru = "Удерживается",
                he = "מוּחזָק"
            ),

        }

        enum class RemainingTime(
            override val en: String,
            override val ru: String,
            override val he: String
        ) : AppI18NProvider.ProvidedValuesA {

            Prefix(
                en = "Prefix",
                ru = "Префикс",
                he = "קידומת"
            ),

            Unknown(
                en = "Unknown",
                ru = "Неизвестно",
                he = "לא ידוע"
            ),

        }

        enum class Error(
            override val en: String,
            override val ru: String,
            override val he: String
        ) : AppI18NProvider.ProvidedValuesA {

            ServerUnavailable(
                en = "Server unavailable",
                ru = "Сервер недоступен",
                he = "השרת לא זמין"
            ),

            UnexpectedServerError(
                en = "Unexpected Server Error",
                ru = "Неожиданная ошибка сервер",
                he = "שגיאת שרת לא צפויה"
            ),

            Forbidden(
                en = "Forbidden",
                ru = "Запрещено",
                he = "אסור"
            ),

        }

    }
}


@VaadinDsl
fun (@VaadinDsl Upload).i18n(locale: Locale): UploadI18N {

    return i18n {

        addFiles = addFiles {
            one = I18n.AddFiles.One.translation(locale) // "Upload File..."
            many = I18n.AddFiles.Many.translation(locale)
        }
        dropFiles = dropFiles {
            one = I18n.DropFiles.One.translation(locale) // "Drop file here"
            many = I18n.DropFiles.Many.translation(locale)
        }
        /**
         * Exception translations
         */
        error = error {
            tooManyFiles = I18n.Error.TooManyFiles.translation(locale)
            fileIsTooBig = I18n.Error.FileIsTooBig.translation(locale)
            incorrectFileType = I18n.Error.IncorrectFileType.translation(locale)
        }
        /**
         * public static class Units implements Serializable {
         *         private List<String> size = Arrays.asList("B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB");
         */

        uploading = uploading {
            // Upload status strings
            status = status {
                connecting = I18n.Uploading.Status.Connecting.translation(locale)
                stalled = I18n.Uploading.Status.Stalled.translation(locale)
                processing = I18n.Uploading.Status.Processing.translation(locale)
                held = I18n.Uploading.Status.Held.translation(locale)
            }
            // remaining time translations
            remainingTime = remainingTime {
                prefix = I18n.Uploading.RemainingTime.Prefix.translation(locale)
                unknown = I18n.Uploading.RemainingTime.Unknown.translation(locale)

            }
            // upload error translations
            error = error {
                serverUnavailable = I18n.Uploading.Error.ServerUnavailable.translation(locale)
                unexpectedServerError = I18n.Uploading.Error.UnexpectedServerError.translation(locale)
                forbidden = I18n.Uploading.Error.Forbidden.translation(locale)
            }
        }

    }

}
