package vlite.digikamweb.ui.base.dialogs

import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.karibudsl.v23.footer
import com.github.mvysny.kaributools.setPrimary
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import kotlinx.css.em
import kotlinx.css.width
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import java.util.*

private enum class I18nEditItemTextDialog(
    override val en: String,
    override val ru: String,
    override val he: String,
) : AppI18NProvider.ProvidedValuesA {

    /*
AddButton(
    en = "Add",
    ru = "Добавить",
    he = "לְהוֹסִיף"
),
*/

    SaveButton(
        en = "Save",
        ru = "Сохранить",
        he = "שמור"
    ),

    CancelButton(
        en = "Cancel",
        ru = "Отмена",
        he = "ביטול"
    ),

    /*
            RequiredErrorMessage(
                en = "Cancel",
                ru = "Отмена",
                he = "ביטול"
            ),
    */

}


fun String.openEditItemTextDialog(
    locale: Locale,
    itemText: String,
    isValid: (newItemText: String) -> Boolean,
    errorMessage: AppI18NProvider.ProvidedValuesA? = null,
    onCancelAction: () -> Unit = {},
    onOkAction: (newItemText: String) -> Unit,
) {
    openDialog {

        lateinit var saveButton: Button
        lateinit var itemTextField: TextField

        fun closeByOk(newItemText: String) {
            close()
            onOkAction(newItemText)
        }

        fun closeByCancel() {
            close()
            onCancelAction()
        }

        fun updateComponentState() {
            val newItemText: String = itemTextField.value
            val isValid = isValid(newItemText)
            itemTextField.isInvalid = !isValid
            if (errorMessage != null) itemTextField.errorMessage = errorMessage.translation(locale).format(newItemText)

            saveButton.isEnabled = isValid && newItemText != itemText
        }

        headerTitle = this@openEditItemTextDialog
        verticalLayout(padding = false) {
            /**
             * [Text Field](https://vaadin.com/docs/latest/components/text-field)
             *
             * PRB: Prevent ESC from clearing text from <input type="search"> on Chrome (or add button to clear text without using type="search")
             * WO: ?
             */
            itemTextField = textField {

                css {
                    width = 50.em
                }

                value = itemText

                isClearButtonVisible = true

                /**
                 * [Validation](https://vaadin.com/docs/latest/components/text-field)
                 */
                isRequired = true

                valueChangeMode = ValueChangeMode.EAGER
                addValueChangeListener { updateComponentState() }
            }
        }
        footer {
            saveButton = button(I18nEditItemTextDialog.SaveButton.translation(locale), VaadinIcon.CHECK.create()) {
                setPrimary()
                onClick { closeByOk(itemTextField.value) }
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

        itemTextField.focus()
    }
}
