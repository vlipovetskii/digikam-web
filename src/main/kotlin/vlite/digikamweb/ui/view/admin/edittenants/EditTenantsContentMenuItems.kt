package vlite.digikamweb.ui.view.admin.edittenants

import com.github.mvysny.karibudsl.v10.item
import com.vaadin.flow.component.contextmenu.HasMenuItems
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.icon.VaadinIcon
import vlite.core.ui.KNotification
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.Tenant
import vlite.digikamweb.domain.objects.TenantName
import vlite.digikamweb.ui.base.dialogs.openDeleteItemConfirmDialog
import vlite.digikamweb.ui.base.dialogs.openEditItemTextDialog
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import java.util.*

private enum class I18n1(
    override val en: String,
    override val ru: String,
    override val he: String,
) : AppI18NProvider.ProvidedValuesA {

    AddTenant(
        en = "Add tenant",
        ru = "Добавить владельца",
        he = "הוסף דייר"
    ),

    RenameTenant(
        en = "Rename tenant “%s”?",
        ru = "Переименовать владельца “%s”?",
        he = "שנה שם דייר “%s”?"
    ),

    DeleteTenantHeader(
        en = "Delete tenant “%s”?",
        ru = "Удалить владельца “%s”?",
        he = "מחק דייר “%s”?"
    ),

    DeleteTenantText(
        en = "Tenant contains %d albums, %d photos, %d person photos",
        ru = "Арендатор содержит %d альбомов, %d фотографий, %d фотографий людей",
        he = "מחק דיירהדייר מכיל %d אלבומים, %d תמונות, %d תמונות של אדם"
    ),

    EditTenantNameErrorMessage(
        en = "Tenant name “%s“ is invalid. Valid tenant name is not empty and does not contain spaces",
        ru = "Имя арендатора “%s“ недействителен. Допустимое имя арендатора не пустое и не содержит пробелов",
        he = "שם הדייר “%s“ אינו חוקי. שם הדייר התקף אינו ריק ואינו מכיל רווחים"
    ),

    ChangeEditAccessCode(
        en = "Change edit access code “%s”",
        ru = "Изменить код доступа для редактирования “%s”",
        he = "לשנות את קוד הגישה לערוך \"%s\""
    ),

    ChangeEditAccessCodeErrorMessage(
        en = "Edit access code “%s“ is invalid. Valid edit access code is not empty, does not contain spaces and has minimum 4 characters",
        ru = "«Код доступа для редактирования “%s“ недействителен. Действительный код доступа для редактирования не является пустым, не содержит пробелов и имеет минимум 4 символа»",
        he = "קוד גישה עריכה “%s“ אינו חוקי. קוד גישה תקף לעריכה אינו ריק, אינו מכיל רווחים ויש לו מינימום 4 תווים"
    ),

    EditAccessCodeChanged(
        en = "Edit access code changed for “%s”",
        ru = "Код доступа для редактирования изменился для «%s»",
        he = "עריכת קוד הגישה השתנה עבור “%s”"
    ),

}

fun HasMenuItems.addTenantMenuItem(
    locale: Locale,
    action: (newTenantName: TenantName) -> Unit
): MenuItem {
    return item(VaadinIcon.PLUS.create(), {

        I18n1.AddTenant
            .translation(locale)
            .openEditItemTextDialog(
                locale,
                "",
                isValid = { TenantName(it).isValid },
                I18n1.EditTenantNameErrorMessage,
            ) { newTenantName ->
                action(TenantName(newTenantName))
            }

    })
}

fun HasMenuItems.renameTenantMenuItem(
    locale: Locale,
    selectedRow: () -> EditTenantsGridRow,
    action: (tenantToRename: Tenant, newTenantName: TenantName) -> Unit
): MenuItem {
    return item(VaadinIcon.EDIT.create(), {

        val tenantToRename = selectedRow().tenant

        I18n1.RenameTenant
            .translation(locale)
            .format(tenantToRename.name.value)
            .openEditItemTextDialog(
                locale,
                tenantToRename.name.value,
                isValid = { TenantName(it).isValid },
                errorMessage = I18n1.EditTenantNameErrorMessage,
            ) { newTenantName ->
                action(tenantToRename, TenantName(newTenantName))
            }

    }) {
        isEnabled = false
    }
}

fun HasMenuItems.deleteTenantMenuItem(
    locale: Locale,
    selectedRow: () -> EditTenantsGridRow,
    childrenCount: (tenantToDelete: Tenant) -> Tenant.ChildrenCount,
    action: (tenantToDelete: Tenant) -> Unit
): MenuItem {
    return item(VaadinIcon.TRASH.create(), {

        val tenantToDelete = selectedRow().tenant

        val childrenCount = childrenCount(tenantToDelete)

        I18n1.DeleteTenantHeader.translation(locale)
            .format(tenantToDelete.name.value)
            .openDeleteItemConfirmDialog(
                locale,
                text = I18n1.DeleteTenantText.takeIf { childrenCount.isNotZero }?.translation(locale)
                    ?.format(childrenCount.albums, childrenCount.photos, childrenCount.personPhotos),
            ) {
                action(tenantToDelete)
            }

    }) { isEnabled = false }
}

fun HasMenuItems.changeEditAccessCodeMenuItem(
    locale: Locale,
    selectedRow: () -> EditTenantsGridRow,
    currentEditAccessCode: (tenantToChangeAccessCode: Tenant) -> EditAccessCode?,
    action: (tenantToChangeAccessCode: Tenant, newEditAccessCode: EditAccessCode) -> Unit
): MenuItem {

    return item(VaadinIcon.PLUS.create(), {

        val tenantToChangeAccessCode = selectedRow().tenant

        I18n1.ChangeEditAccessCode
            .translation(locale)
            .format(tenantToChangeAccessCode.name.value)
            .openEditItemTextDialog(
                locale,
                currentEditAccessCode(tenantToChangeAccessCode)?.value.orEmpty(),
                isValid = { EditAccessCode(it).isValid },
                errorMessage = I18n1.ChangeEditAccessCodeErrorMessage,
            ) { newEditAccessCode ->

                action(tenantToChangeAccessCode, EditAccessCode(newEditAccessCode))

                KNotification.info(
                    I18n1.EditAccessCodeChanged.translation(locale)
                        .format(tenantToChangeAccessCode.name.value)
                )
            }

    }) {
        isEnabled = false
    }

}

