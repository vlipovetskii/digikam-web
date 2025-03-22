package vlite.digikamweb.ui.view.editor.editpersonphotos

import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.menuBar
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.FlexComponent
import vlite.core.ui.configureWithFlexGrow
import vlite.core.ui.content
import vlite.core.ui.removeContent
import vlite.core.ui.selectedItem
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA
import vlite.digikamweb.ui.view.editor.editpersonphotos.dialogs.openRenameExistingPersonPhotoConfirmDialog

class EditPersonPhotoContent : EditPersonPhotoContentA {

    private lateinit var editPersonPhotoGrid: Grid<EditPersonPhotoGridRow>

    private lateinit var deletePersonPhotoMenuItem: MenuItem
    private lateinit var renamePersonPhotoMenuItem: MenuItem

    override fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA) {

        removeContent()
        content {

            horizontalLayout {

                alignItems = FlexComponent.Alignment.START

                isPadding = false
                setHeightFull()

                verticalLayout {

                    configureWithFlexGrow(5.0)

                    fun gridRows() = photoStorage.allPersonPhotos().map { EditPersonPhotoGridRow(it) }.toList()

                    fun refreshGridRows(newPersonPhoto: PersonPhoto? = null) {
                        val gridRows = gridRows()

                        editPersonPhotoGrid.setItems(gridRows)
                        // editorAlbumGrid.refresh() is not required
                        if (newPersonPhoto != null) {
                            gridRows.first { row -> row.personPhoto == newPersonPhoto }.also { newRow ->
                                editPersonPhotoGrid.scrollToItem(newRow)
                                editPersonPhotoGrid.select(newRow)
                            }
                        }

                    }

                    horizontalLayout {
                        //isPadding = false

                        uploadPersonPhotoButton { newPersonPhotoName, fileContent ->

                            // TODO Arrange PersonName data entry immediately

                            val newPersonPhoto = photoStorage.addPersonPhoto(
                                newPersonPhotoName,
                                fileContent
                            )
                            refreshGridRows(newPersonPhoto)
                        }

                        menuBar {

/*
TODO How to invoke upload from menu item ?
                            val a = item(VaadinIcon.UPLOAD.create())
                            upload.button { a }
*/

                            // TODO display deletePhotoButton inside GridRow close to picture
                            deletePersonPhotoMenuItem = deletePersonPhotoMenuItem(
                                appLayoutLocale,
                                selectedRow = { editPersonPhotoGrid.selectedItem }
                            ) { personPhotoToDelete ->
                                photoStorage.removePersonPhoto(personPhotoToDelete)
                                refreshGridRows()
                            }

                            renamePersonPhotoMenuItem = renamePersonPhotoMenuItem(
                                appLayoutLocale,
                                selectedRow = { editPersonPhotoGrid.selectedItem }
                            ) { personPhotoToRename, newPersonPhotoName ->
                                if (photoStorage.personPhoto(newPersonPhotoName) != null) {

                                    newPersonPhotoName
                                        .openRenameExistingPersonPhotoConfirmDialog(appLayoutLocale) {
                                            val newPersonPhoto =
                                                photoStorage.renamePersonPhoto(
                                                    personPhotoToRename,
                                                    newPersonPhotoName,
                                                    renameExisting = true,
                                                )
                                            refreshGridRows(newPersonPhoto)
                                        }

                                } else {

                                    val newPersonPhoto =
                                        photoStorage.renamePersonPhoto(personPhotoToRename, newPersonPhotoName)
                                    refreshGridRows(newPersonPhoto)

                                }
                            }

                        }


                    }

                    editPersonPhotoGrid = editPersonPhotoGrid { isRowSelected ->

                        deletePersonPhotoMenuItem.isEnabled = isRowSelected
                        renamePersonPhotoMenuItem.isEnabled = isRowSelected

                    }
                    refreshGridRows()

                }

            }

        }

    }

}