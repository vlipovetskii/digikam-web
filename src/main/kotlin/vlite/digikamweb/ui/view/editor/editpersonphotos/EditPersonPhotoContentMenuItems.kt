package vlite.digikamweb.ui.view.editor.editpersonphotos

import com.github.mvysny.karibudsl.v10.item
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.contextmenu.HasMenuItems
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.icon.VaadinIcon
import vlite.core.ui.upload.upload
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.PhotoName
import vlite.digikamweb.domain.objects.base.BasePhotoA
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.ui.base.dialogs.openDeleteItemConfirmDialog
import vlite.digikamweb.ui.base.dialogs.openEditItemTextDialog
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import java.util.*

private enum class I18n(
    override val en: String,
    override val ru: String,
    override val he: String,
) : AppI18NProvider.ProvidedValuesA {

    DeletePhoto(
        en = "Delete photo “%s”?",
        ru = "Удалить фото “%s”?",
        he = "מחק תמונה “%s”?"
    ),

    RenamePhotoName(
        en = "Rename photo “%s”?",
        ru = "Переименовать “%s”?",
        he = "שנה שם תמונה “%s”?"
    ),

    RenamePhotoNameErrorMessage(
        en = "Photo name “%s“ is invalid. Valid photo name is not empty and does not contain spaces",
        ru = "Название фотографии “%s“ является некорректным. Корректное название фотографии не пустое и не содержит пробелов",
        he = "שם הצילום “%s“ אינו חוקי. שם תמונה תקף אינו ריק ואינו מכיל רווחים"
    ),

}

fun HasComponents.uploadPersonPhotoButton(
    action: (newPersonPhotoName: PersonName, fileContent: ByteArray) -> Unit
) {
    upload(
        maxFileSize = Photo.MAX_SIZE,
        BasePhotoA.MIME_TYPE,
    ) {

        onFileReceived = { fileName, fileMimeType, fileContent ->
            // "`$fileName`, `$fileMimeType`, `${fileContent.size}`".notifyInfo()
            action(PersonName(PhotoName.fromFileNameWithExtension(fileName).value), fileContent)
            clearUploadedFileList()
        }

    }
}

fun HasMenuItems.deletePersonPhotoMenuItem(
    locale: Locale,
    selectedRow: () -> EditPersonPhotoGridRow,
    action: (personPhotoToDelete: PersonPhoto) -> Unit
): MenuItem {
    return item(VaadinIcon.TRASH.create(), {

        val personPhotoToDelete = selectedRow().personPhoto

        I18n.DeletePhoto.translation(locale)
            .format(personPhotoToDelete.name.value)
            .openDeleteItemConfirmDialog(locale) {
                action(personPhotoToDelete)
            }

    }) { isEnabled = false }
}

fun HasMenuItems.renamePersonPhotoMenuItem(
    locale: Locale,
    selectedRow: () -> EditPersonPhotoGridRow,
    action: (personPhotoToRename: PersonPhoto, newPersonPhotoName: PersonName) -> Unit
): MenuItem {
    return item(VaadinIcon.EDIT.create(), {

        val personPhotoToRename = selectedRow().personPhoto

        I18n.RenamePhotoName
            .translation(locale)
            .format(personPhotoToRename.name.value)
            .openEditItemTextDialog(
                locale,
                personPhotoToRename.name.value,
                isValid = { PhotoName(it).isValid },
                errorMessage = I18n.RenamePhotoNameErrorMessage,
            ) { newPersonName ->
                action(personPhotoToRename, PersonName(newPersonName))
            }
    }) { isEnabled = false }

}
