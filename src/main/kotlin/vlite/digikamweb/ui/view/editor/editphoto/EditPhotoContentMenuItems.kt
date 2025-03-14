package vlite.digikamweb.ui.view.editor.editphoto

import com.github.mvysny.karibudsl.v10.item
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.contextmenu.HasMenuItems
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.icon.VaadinIcon
import vlite.core.ui.upload.upload
import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.objects.PhotoName
import vlite.digikamweb.domain.objects.base.BasePhotoA
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

fun HasComponents.uploadPhotoButton(
    action: (newPhotoName: PhotoName, fileContent: ByteArray) -> Unit
) {
    upload(
        maxFileSize = Photo.MAX_SIZE,
        BasePhotoA.MIME_TYPE,
    ) {

        onFileReceived = { fileName, fileMimeType, fileContent ->
            // "`$fileName`, `$fileMimeType`, `${fileContent.size}`".notifyInfo()
            action(PhotoName.fromFileNameWithExtension(fileName), fileContent)
            clearUploadedFileList()
        }

    }
}

fun HasMenuItems.deletePhotoMenuItem(
    locale: Locale,
    selectedRow: () -> EditPhotoGridRow,
    action: (photoToDelete: Photo) -> Unit
): MenuItem {
    return item(VaadinIcon.TRASH.create(), {

        val photoToDelete = selectedRow().photo

        I18n.DeletePhoto.translation(locale)
            .format(photoToDelete.name.value)
            .openDeleteItemConfirmDialog(locale) {
                action(photoToDelete)
            }

    }) { isEnabled = false }
}

fun HasMenuItems.renamePhotoMenuItem(
    locale: Locale,
    selectedRow: () -> EditPhotoGridRow,
    action: (photoToRename: Photo, newPhotoName: PhotoName) -> Unit
): MenuItem {
    return item(VaadinIcon.EDIT.create(), {


        val photoToRename = selectedRow().photo

        I18n.RenamePhotoName
            .translation(locale)
            .format(photoToRename.name.value)
            .openEditItemTextDialog(
                locale,
                photoToRename.name.value,
                isValid = { PhotoName(it).isValid },
                errorMessage = I18n.RenamePhotoNameErrorMessage,
            ) { newPhotoName ->
                action(photoToRename, PhotoName(newPhotoName))
            }
    }) { isEnabled = false }

}

fun HasMenuItems.movePhotoMenuItem(
    albumsToMoveInto: () -> Sequence<Album>,
    selectedRow: () -> EditPhotoGridRow,
    action: (photoToMove: Photo, albumToMoveInto: Album) -> Unit
): MenuItem {
    return item(VaadinIcon.FILE_TREE_SUB.create()) {

        albumsToMoveInto().forEach { albumToMoveInto ->
            item(albumToMoveInto.name.value, {
                val photoToMove = selectedRow().photo
                action(photoToMove, albumToMoveInto)
            })
        }

        isEnabled = false
    }

}
