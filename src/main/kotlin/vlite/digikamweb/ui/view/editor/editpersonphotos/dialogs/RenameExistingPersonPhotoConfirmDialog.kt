package vlite.digikamweb.ui.view.editor.editpersonphotos.dialogs

import com.github.mvysny.karibudsl.v23.openConfirmDialog
import vlite.core.ui.setCloseOnCancel
import vlite.core.ui.standardCloseOnCancelButton
import vlite.core.ui.standardConfirmButton
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import java.util.*

private enum class I18nRenameExistingPersonPhotoConfirmDialog(
    override val en: String,
    override val ru: String,
    override val he: String,
) : AppI18NProvider.ProvidedValuesA {

    RenameButton(
        en = "Rename",
        ru = "Переименовать",
        he = "שנה שם"
    ),

    CancelButton(
        en = "Cancel",
        ru = "Отмена",
        he = "ביטול"
    ),

    PersonPhotoWithTheNameAlreadyExists(
        en = "Person photo with the name “%s” already exists. Rename?",
        ru = "Фотография с именем “%s” уже существует. Переименовать?",
        he = "תמונה של אדם עם השם ?“%s” כבר קיימת. שינוי שם"
    ),

}

fun PersonName.openRenameExistingPersonPhotoConfirmDialog(
    locale: Locale,
    text: String? = null,
    onConfirmButtonClick: () -> Unit
) {

    /**
     * [Confirm Dialog] https://vaadin.com/docs/latest/components/confirm-dialog
     * SEE [openConfirmDialog]
     */
    openConfirmDialog(I18nRenameExistingPersonPhotoConfirmDialog.PersonPhotoWithTheNameAlreadyExists.translation(locale).format(this.value), text) {

        setConfirmButton(
            standardConfirmButton(
                I18nRenameExistingPersonPhotoConfirmDialog.RenameButton.translation(locale),
                onConfirmButtonClick = onConfirmButtonClick
            )
        )
        setCloseOnCancel(standardCloseOnCancelButton(I18nRenameExistingPersonPhotoConfirmDialog.CancelButton.translation(locale)))

    }

}

