package vlite.digikamweb.ui.base.dialogs

import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onClick
import com.github.mvysny.karibudsl.v23.openConfirmDialog
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.icon.VaadinIcon
import vlite.core.ui.buildSingleComponent
import vlite.core.ui.setCloseOnCancelButton
import vlite.core.ui.setConfirmIsDanger
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
            buildSingleComponent {
                button(
                    I18nDeleteItemConfirmDialog.DeleteButton.translation(locale),
                    VaadinIcon.CHECK.create()
                ) {
                    setConfirmIsDanger()
                    onClick {
                        onConfirmButtonClick()
                    }
                }
            }
        )

        setCloseOnCancelButton(buildSingleComponent {
            button(
                I18nDeleteItemConfirmDialog.CancelButton.translation(locale),
                VaadinIcon.ARROW_BACKWARD.create()
            )
        } as Button)

    }

}

