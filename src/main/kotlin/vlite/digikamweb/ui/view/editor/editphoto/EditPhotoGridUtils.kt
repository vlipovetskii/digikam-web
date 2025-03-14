package vlite.digikamweb.ui.view.editor.editphoto

import com.vaadin.flow.component.grid.Grid
import vlite.core.ui.refreshRow
import vlite.core.ui.selectedRow
import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.domain.services.PhotoPersonMetadataA
import vlite.digikamweb.domain.services.storage.PhotoStorageA

fun Grid<EditPhotoGridRow>.refreshPhoto(photo: Photo) {
    selectedRow.photo = photo
    refreshRow(selectedRow)
}

fun Grid<EditPhotoGridRow>.refreshRows(photoStorage: PhotoStorageA, album: Album, newPhoto: Photo? = null) {
    val gridRows = photoStorage.photos(album).map { EditPhotoGridRow(it) }.toList()

    setItems(gridRows)
    // editorAlbumGrid.refresh() is not required
    if (newPhoto != null) {
        gridRows.first { row -> row.photo == newPhoto }.also { newRow ->
            scrollToItem(newRow)
            select(newRow)
        }
    }

}

fun Grid<EditPhotoGridRow>.updateInPhoto(
    photoPersonMetadata: PhotoPersonMetadataA,
    personName: PersonName,
    add: Boolean
) {

    val currentPhotoPersonNames = selectedRow.photo.personNames

    if (add) {
        if (personName !in currentPhotoPersonNames) {
            refreshPhoto(
                photoPersonMetadata.addPersonName(
                    selectedRow.photo,
                    personName
                )
            )
        }
    } else {
        if (personName in currentPhotoPersonNames) {
            refreshPhoto(
                photoPersonMetadata.removePersonName(
                    selectedRow.photo,
                    personName
                )
            )
        }
    }

}
