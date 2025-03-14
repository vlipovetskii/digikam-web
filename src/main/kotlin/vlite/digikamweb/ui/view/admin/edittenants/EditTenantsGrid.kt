package vlite.digikamweb.ui.view.admin.edittenants

import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.column
import com.github.mvysny.karibudsl.v10.grid
import com.github.mvysny.karibudsl.v10.onClick
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import vlite.core.ui.componentColumn
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.Tenant
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import vlite.digikamweb.ui.base.view.OnRowSelected
import vlite.digikamweb.ui.base.view.defaultConfiguration
import vlite.digikamweb.ui.view.editor.EditorView
import vlite.digikamweb.ui.view.main.MainView
import java.util.*

private enum class I18n(
    override val en: String,
    override val ru: String,
    override val he: String
) : AppI18NProvider.ProvidedValuesA {

    NavigateToEditorView(
        en = "Edit albums",
        ru = "Редактировать альбомы",
        he = "ערוך אלבומים"
    ),

    NavigateToMainView(
        en = "View albums",
        ru = "Посмотреть альбомы",
        he = "צפה באלבומים"
    ),

}

data class EditTenantsGridRow(override val tenant: Tenant, override val editAccessCode: EditAccessCode?) : Tenant.Holder, EditAccessCode.NullableHolder

fun VerticalLayout.editTenantsGrid(
    locale : Locale,
    onRowSelected: OnRowSelected<EditTenantsGridRow>
): Grid<EditTenantsGridRow> {
    return grid<EditTenantsGridRow> {

        defaultConfiguration(onRowSelected)

        componentColumn({ row ->
            button(I18n.NavigateToEditorView.translation(locale), VaadinIcon.PAPERPLANE.create()) {
                isEnabled = row.editAccessCode != null

                onClick {
                    EditorView.navigateToMe(row.tenant.name, row.editAccessCode!!)
                }

            }
        })

        componentColumn({ row ->
            button(I18n.NavigateToMainView.translation(locale), VaadinIcon.PAPERPLANE.create()) {
                isEnabled = row.editAccessCode != null

                onClick {
                    MainView.navigateToMe(row.tenant.name, row.editAccessCode!!)
                }

            }
        })

        column({ row ->
            row.tenant.name.value
        }) {
            //setHeader("")
        }

    }
}


