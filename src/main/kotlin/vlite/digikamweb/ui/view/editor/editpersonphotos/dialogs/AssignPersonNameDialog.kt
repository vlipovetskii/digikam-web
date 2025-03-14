package vlite.digikamweb.ui.view.editor.editpersonphotos.dialogs

import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.karibudsl.v23.footer
import com.github.mvysny.kaributools.setPrimary
import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.select.Select
import kotlinx.css.em
import kotlinx.css.width
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import java.util.*

private enum class I18nEditItemTextDialog(
    override val en: String,
    override val ru: String,
    override val he: String,
) : AppI18NProvider.ProvidedValuesA {

    OkButton(
        en = "Ok",
        ru = "Ok",
        he = "Ok"
    ),

    CancelButton(
        en = "Cancel",
        ru = "Отмена",
        he = "ביטול"
    ),

}

fun String.openAssignPersonNameDialog(
    locale: Locale,
    allPersonNames: Sequence<PersonName>,
    origPersonPhotoRename: PersonName,
    onCancelAction: () -> Unit = {},
    onOkAction: (newPersonPhotoName: PersonName) -> Unit,
) {
    openDialog {

        lateinit var saveButton: Button
        lateinit var personNameSelectField: Select<PersonName>

        fun closeByOk(newPersonPhotoName: PersonName) {
            close()
            onOkAction(newPersonPhotoName)
        }

        fun closeByCancel() {
            close()
            onCancelAction()
        }

        fun updateComponentState() {
            val newPersonPhotoName: PersonName? = personNameSelectField.value

            saveButton.isEnabled = newPersonPhotoName != origPersonPhotoRename
        }

        headerTitle = this@openAssignPersonNameDialog
        verticalLayout(padding = false) {
            /**
             * [Select](https://vaadin.com/docs/latest/components/select)
             */
            personNameSelectField = select {

                css {
                    width = 50.em
                }

                itemLabelGenerator = ItemLabelGenerator {item ->
                    item.value
                }

                setItems(allPersonNames.filterNot { it == origPersonPhotoRename }.toList())

                addValueChangeListener { updateComponentState() }
            }
        }
        footer {
            saveButton = button(I18nEditItemTextDialog.OkButton.translation(locale), VaadinIcon.CHECK.create()) {
                setPrimary()
                onClick { closeByOk(personNameSelectField.value) }
            }
            button(I18nEditItemTextDialog.CancelButton.translation(locale), VaadinIcon.ARROW_BACKWARD.create()) {
                onClick { closeByCancel() }
            }
        }
        /**
         * See comments on [Dialog.addDialogCloseActionListener]
         */
        addDialogCloseActionListener { closeByCancel() }

        updateComponentState()

        personNameSelectField.focus()
    }
}
