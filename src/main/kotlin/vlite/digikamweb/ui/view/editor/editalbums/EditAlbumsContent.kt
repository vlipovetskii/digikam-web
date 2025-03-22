package vlite.digikamweb.ui.view.editor.editalbums

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
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

class EditAlbumsContent(
) : EditAlbumsContentA {

    private lateinit var editAlbumsGrid: Grid<EditAlbumsGridRow>

    private lateinit var addAlbumMenuItem: MenuItem
    private lateinit var renameAlbumMenuItem: MenuItem
    private lateinit var deleteAlbumMenuItem: MenuItem

    override fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA) {

        removeContent()
        content {

            horizontalLayout {

                alignItems = FlexComponent.Alignment.START

                isPadding = false
                setHeightFull()

                verticalLayout {

                    configureWithFlexGrow(5.0)

                    fun gridRows() = photoStorage.albums().map { EditAlbumsGridRow(it) }.toList()

                    fun refreshGridRows(newAlbum: Album? = null) {
                        val gridRows = gridRows()

                        editAlbumsGrid.setItems(gridRows)
                        // editorAlbumGrid.refresh() is not required
                        if (newAlbum != null) {
                            gridRows.first { row -> row.album == newAlbum }.also { newRow ->
                                editAlbumsGrid.scrollToItem(newRow)
                                editAlbumsGrid.select(newRow)
                            }
                        }

                    }

                    horizontalLayout {
                        //isPadding = false

                        menuBar {
                            addAlbumMenuItem = addAlbumMenuItem(appLayoutLocale) { newAlbumName ->
                                refreshGridRows(photoStorage.addAlbum(newAlbumName))
                            }

                            // TODO display renameAlbumButton inside GridRow close to picture
                            renameAlbumMenuItem = renameAlbumMenuItem(
                                appLayoutLocale,
                                selectedRow = { editAlbumsGrid.selectedItem }
                            ) { albumToRename, newAlbumName ->
                                refreshGridRows(photoStorage.renameAlbum(albumToRename, newAlbumName))
                            }

                            // TODO display deletePhotoButton inside GridRow close to picture
                            deleteAlbumMenuItem = deleteAlbumMenuItem(
                                appLayoutLocale,
                                selectedRow = { editAlbumsGrid.selectedItem },
                                childrenCount = { albumToDelete -> photoStorage.photos(albumToDelete).count() },
                            ) { albumToDelete ->
                                photoStorage.removeAlbum(albumToDelete)
                                refreshGridRows()
                            }

                        }
                    }

                    editAlbumsGrid = editAlbumsGrid { isRowSelected ->

                        deleteAlbumMenuItem.isEnabled = isRowSelected
                        renameAlbumMenuItem.isEnabled = isRowSelected

                    }
                    refreshGridRows()

                }

                verticalLayout {

                    configureWithFlexGrow(1.0)

                    isVisible = false

                    // albumsEditor = albumEditor(photoStorage, editAlbumsGrid)

                }

            }

        }

    }

}



