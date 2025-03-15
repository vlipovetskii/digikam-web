package vlite.digikamweb.ui.base.dialogs

import com.github.mvysny.karibudsl.v10.css
import com.github.mvysny.karibudsl.v10.onClick
import com.github.mvysny.karibudsl.v23.openConfirmDialog
import com.vaadin.flow.component.icon.VaadinIcon
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import vlite.core.ui.setCloseOnCancel
import vlite.core.ui.setConfirm
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

        setConfirm(I18nDeleteItemConfirmDialog.DeleteButton.translation(locale), VaadinIcon.CHECK.create()) {

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
        setCloseOnCancel(I18nDeleteItemConfirmDialog.CancelButton.translation(locale))

    }

}

