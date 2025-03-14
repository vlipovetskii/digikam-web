package vlite.digikamweb.ui.view.editor.editpersonphotos.dialogs

import com.github.mvysny.karibudsl.v10.css
import com.github.mvysny.karibudsl.v10.onClick
import com.github.mvysny.karibudsl.v23.openConfirmDialog
import com.vaadin.flow.component.icon.VaadinIcon
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import vlite.core.ui.confirmButton
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

        confirmButton(I18nRenameExistingPersonPhotoConfirmDialog.RenameButton.translation(locale), VaadinIcon.CHECK.create()) {

            css {
                backgroundColor = Color.red
            }

            onClick {
                onConfirmButtonClick()
            }

        }

        // setConfirmIsDanger()
        /*
                                            cancelButton("Cancel", VaadinIcon.ARROW_BACKWARD.create()) {
                                                onClick {
                                                    this@openConfirmDialog.close()
                                                }
                                            }
        */
        /*
            setCancelButton(
                Button("Cancel", VaadinIcon.ARROW_BACKWARD.create()).apply {
                    onClick {
                        close()
                    }
                }
            )
        */
        /**
         * PRB: setCancelButton(component: Component) is not displayed
         * WO: setCancelButton("Cancel") { close() }
         * Solution: TODO issue to Vaadin github repo
         */
        setCancelButton(I18nRenameExistingPersonPhotoConfirmDialog.CancelButton.translation(locale)) { close() }

    }

}

