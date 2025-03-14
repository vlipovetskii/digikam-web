package vlite.digikamweb.ui.view.main.albums

import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.FlexComponent
import vlite.core.ui.configureWithFlexGrow
import vlite.core.ui.content
import vlite.core.ui.removeContent
import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA
import vlite.digikamweb.ui.base.view.hideOrShow
import vlite.digikamweb.ui.view.main.components.PhotoDetails
import vlite.digikamweb.ui.view.main.components.photoDetails

class AlbumContent : AlbumContentA {

    private lateinit var albumGrid: Grid<AlbumGridRow>
    private lateinit var photoDetails: PhotoDetails

    override fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA, album: Album) {

        removeContent()
        content {

            horizontalLayout {

                alignItems = FlexComponent.Alignment.START

                isPadding = false
                setHeightFull()

                verticalLayout {

                    configureWithFlexGrow(5.0)

                    // { peopleEditor }, because peopleEditor is initialized later (below).
                    albumGrid = albumGrid(appLayoutLocale) { isRowSelected ->

                        photoDetails.hideOrShow(isRowSelected) {
                            setPhotoPersonNames()
                        }

                    }
                    albumGrid.setItems(photoStorage.photos(album).map { AlbumGridRow(it) }.toList())

                }

                verticalLayout {

                    configureWithFlexGrow(1.0)

                    isVisible = false

                    photoDetails = photoDetails(photoStorage, albumGrid)

                }

            }

        }

    }

}

