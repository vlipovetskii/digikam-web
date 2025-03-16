package vlite.digikamweb.ui.base.dialogs

import com.github.mvysny.karibudsl.v23.openConfirmDialog
import vlite.core.ui.setCloseOnCancel
import vlite.core.ui.standardCloseOnCancelButton
import vlite.core.ui.standardConfirmButton
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import java.util.*

private enum class I18nDeleteItemConfirmDialog(
    override val en: String,
    override val ru: String,
    override val he: String,
) : AppI18NProvider.ProvidedValuesA {

    DeleteButton(
        en = "Delete",
        ru = "Удалить",
        he = "לִמְחוֹק"
    ),

    CancelButton(
        en = "Cancel",
        ru = "Отмена",
        he = "ביטול"
    ),
}

fun String.openDeleteItemConfirmDialog(
    locale: Locale,
    text: String? = null,
    onConfirmButtonClick: () -> Unit
) {

    /**
     * [Confirm Dialog] https://vaadin.com/docs/latest/components/confirm-dialog
     * SEE [openConfirmDialog]
     */
    openConfirmDialog(this, text) {

        setConfirmButton(
            standardConfirmButton(
                I18nDeleteItemConfirmDialog.DeleteButton.translation(locale),
                onConfirmButtonClick = onConfirmButtonClick
            )
        )
        setCloseOnCancel(standardCloseOnCancelButton(I18nDeleteItemConfirmDialog.CancelButton.translation(locale)))
    }
}

