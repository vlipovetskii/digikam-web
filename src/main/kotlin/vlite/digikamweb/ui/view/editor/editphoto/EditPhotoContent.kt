package vlite.digikamweb.ui.view.editor.editphoto

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
import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.domain.services.PhotoPersonMetadataA
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.domain.services.storage.renamePersonName
import vlite.digikamweb.ui.base.view.BaseAppLayoutA
import vlite.digikamweb.ui.base.view.hideOrShow
import vlite.digikamweb.ui.view.editor.components.PhotoDetailsEditor
import vlite.digikamweb.ui.view.editor.components.photoDetailsEditor

class EditPhotoContent(
    override val photoPersonMetadata: PhotoPersonMetadataA,
) : EditPhotoContentA,
    PhotoPersonMetadataA.Holder {

    private lateinit var editPhotoGrid: Grid<EditPhotoGridRow>

    private lateinit var photoDetailsEditor: PhotoDetailsEditor

    private lateinit var deletePhotoMenuItem: MenuItem
    private lateinit var renamePhotoMenuItem: MenuItem
    private lateinit var movePhotoMenuItem: MenuItem

    override fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA, album: Album) {

        removeContent()
        content {

            horizontalLayout {

                alignItems = FlexComponent.Alignment.START

                isPadding = false
                setHeightFull()

                verticalLayout {

                    configureWithFlexGrow(5.0)

                    fun refreshGridRows(newPhoto: Photo? = null) {
                        editPhotoGrid.refreshRows(photoStorage, album, newPhoto)
                    }

                    horizontalLayout {
                        //isPadding = false

                        uploadPhotoButton { newPhotoName, fileContent ->

                            val newPhoto = photoStorage.addPhoto(
                                album,
                                newPhotoName,
                                fileContent
                            )
                            refreshGridRows(newPhoto)
                        }

                        /**
                         * [Menu Bar](https://vaadin.com/docs/latest/components/menu-bar)
                         */
                        menuBar {

                            // TODO display deletePhotoButton inside GridRow close to picture
                            deletePhotoMenuItem = deletePhotoMenuItem(
                                appLayoutLocale,
                                selectedRow = { editPhotoGrid.selectedItem }
                            ) { photoToDelete ->
                                photoStorage.removePhoto(photoToDelete)
                                refreshGridRows()
                            }

                            renamePhotoMenuItem = renamePhotoMenuItem(
                                appLayoutLocale,
                                selectedRow = { editPhotoGrid.selectedItem }
                            ) { photoToRename, newPhotoName ->
                                refreshGridRows(photoStorage.renamePhoto(photoToRename, newPhotoName))
                            }

                            movePhotoMenuItem = movePhotoMenuItem(
                                albumsToMoveInto = { photoStorage.albums().filterNot { it == album } },
                                selectedRow = { editPhotoGrid.selectedItem },
                            ) { photoToMove, albumToMoveInto ->
                                photoStorage.movePhoto(photoToMove, albumToMoveInto)
                                refreshGridRows()
                            }

                        }

                    }

                    editPhotoGrid = editPhotoGrid(appLayoutLocale) { isRowSelected ->

                        photoDetailsEditor.hideOrShow(isRowSelected) {
                            populatePersonNames()
                        }

                        deletePhotoMenuItem.isEnabled = isRowSelected
                        renamePhotoMenuItem.isEnabled = isRowSelected
                        movePhotoMenuItem.isEnabled = isRowSelected

                    }
                    refreshGridRows()

                }

                verticalLayout {

                    configureWithFlexGrow(1.0)

                    isVisible = false

                    photoDetailsEditor = photoDetailsEditor(
                        photoStorage,
                        selectedRow = { editPhotoGrid.selectedItem },
                        updateInPhoto = { add ->
                            editPhotoGrid.updateInPhoto(photoPersonMetadata, this, add)
                        },
                        renamePersonName = { oldPersonName, newPersonName ->
                            photoStorage.renamePersonName(photoPersonMetadata, oldPersonName, newPersonName).also {
                                editPhotoGrid.refreshRows(photoStorage, album, editPhotoGrid.selectedItem.photo)
                            }
                        },
                    )

                }

            }

        }

    }

}



