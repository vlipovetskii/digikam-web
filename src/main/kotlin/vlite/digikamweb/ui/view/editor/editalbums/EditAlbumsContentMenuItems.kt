package vlite.digikamweb.ui.view.editor.editalbums

import com.github.mvysny.karibudsl.v10.item
import com.vaadin.flow.component.contextmenu.HasMenuItems
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.icon.VaadinIcon
import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.objects.AlbumName
import vlite.digikamweb.ui.base.dialogs.openDeleteItemConfirmDialog
import vlite.digikamweb.ui.base.dialogs.openEditItemTextDialog
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import java.util.*

private enum class I18n(
    override val en: String,
    override val ru: String,
    override val he: String,
) : AppI18NProvider.ProvidedValuesA {

    AddAlbum(
        en = "Add photo album",
        ru = "Добавить фото альбом",
        he = "הוסף אלבום תמונות"
    ),

    RenameAlbum(
        en = "Rename photo album “%s”?",
        ru = "Переименовать фото альбом “%s”?",
        he = "שנה שם אלבום תמונות “%s”?"
    ),

    DeleteAlbumHeader(
        en = "Delete photo album “%s”?",
        ru = "Удалить фото альбом “%s”?",
        he = "מחק אלבום תמונות “%s”?"
    ),

    DeleteAlbumText(
        en = "Album contains %d photos?",
        ru = "Альбом содержит %d фотографий",
        he = "האלבום מכיל %d תמונות"
    ),

    EditAlbumNameErrorMessage(
        en = "Photo album name “%s“ is invalid. Valid photo album name is not empty and does not contain spaces",
        ru = "Название фотоальбома “%s“ является некорректным. Корректное название фотоальбома не пустое и не содержит пробелов",
        he = "שם אלבום הצילום “%s“ אינו חוקי. שם אלבום תמונה תקף אינו ריק ואינו מכיל רווחים"
    ),

}

fun HasMenuItems.addAlbumMenuItem(
    locale: Locale,
    action: (newAlbumName: AlbumName) -> Unit
): MenuItem {
    return item(VaadinIcon.PLUS.create(), {

        I18n.AddAlbum
            .translation(locale)
            .openEditItemTextDialog(
                locale,
                "",
                isValid = { AlbumName(it).isValid },
                errorMessage = I18n.EditAlbumNameErrorMessage,
            ) { newAlbumName ->
                action(AlbumName(newAlbumName))
            }

    })
}

fun HasMenuItems.renameAlbumMenuItem(
    locale: Locale,
    selectedRow: () -> EditAlbumsGridRow,
    action: (albumToRename: Album, newAlbumName: AlbumName) -> Unit
): MenuItem {
    return item(VaadinIcon.EDIT.create(), {

        val albumToRename = selectedRow().album

        I18n.RenameAlbum
            .translation(locale)
            .openEditItemTextDialog(
                locale,
                albumToRename.name.value,
                isValid = { AlbumName(it).isValid },
                errorMessage = I18n.EditAlbumNameErrorMessage,
            ) { newAlbumName ->
                action(albumToRename, AlbumName(newAlbumName))
            }

    }) { isEnabled = false }

}


fun HasMenuItems.deleteAlbumMenuItem(
    locale: Locale,
    selectedRow: () -> EditAlbumsGridRow,
    childrenCount: (albumToDelete: Album) -> Int,
    action: (albumToDelete: Album) -> Unit
): MenuItem {
    return item(VaadinIcon.TRASH.create(), {

        val albumToDelete = selectedRow().album

        val childrenCount = childrenCount(albumToDelete)

        I18n.DeleteAlbumHeader.translation(locale)
            .format(albumToDelete.name.value)
            .openDeleteItemConfirmDialog(
                locale,
                text = I18n.DeleteAlbumText.takeIf { childrenCount > 0 }?.translation(locale)?.format(childrenCount)
            ) {
                action(albumToDelete)
            }

    }) { isEnabled = false }

}

