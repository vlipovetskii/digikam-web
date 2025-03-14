package vlite.digikamweb.ui.view.main.components

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.listbox.ListBox
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.data.renderer.ComponentRenderer
import vlite.core.ui.selectedRowOrNull
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.image
import vlite.digikamweb.ui.base.view.SideComponentA
import vlite.digikamweb.ui.view.main.albums.AlbumGridRow

/**
 * [Creating a Component Using Existing Components](https://vaadin.com/docs/latest/flow/create-ui/creating-components/composite)
 * [Toolbar](https://github.com/mvysny/karibu-dsl/blob/master/example/src/main/kotlin/com/vaadin/starter/beveragebuddy/ui/Toolbar.kt)
 */
class PhotoDetails(
    override val photoStorage: PhotoStorageA,
    private val albumGrid: Grid<AlbumGridRow>,
) : SideComponentA(),
    PhotoStorageA.Holder {

    lateinit var personNameListBox: ListBox<PersonPhoto>

    /**
     * [PhotoDetails] Api
     */

    fun setPhotoPersonNames() {
        personNameListBox.setItems(
            (albumGrid.selectedRowOrNull?.photo?.personNames ?: emptySet())
                .map {
                    photoStorage.personPhoto(it)
                }
        )
    }

    @Suppress("unused")
    private val root: Div = ui {
        div {

            verticalLayout {
                isPadding = false
                setHeightFull()

                /**
                 * [List Box](https://vaadin.com/docs/latest/components/list-box)
                 */
                personNameListBox = listBox<PersonPhoto> {
                    setRenderer(ComponentRenderer { personPhoto ->
                        HorizontalLayout().apply {
                            isPadding = false
                            alignItems = FlexComponent.Alignment.BASELINE

                            // java.nio.file.NoSuchFileException
                            //if (personPhoto.fileExists)
                            image(personPhoto)
                            span(personPhoto.name.value)
                        }
                        /* Image("") */
                    })

                }

            }
        }
    }

}

@VaadinDsl
fun (@VaadinDsl HasComponents).photoDetails(
    photoStorage: PhotoStorageA,
    albumGrid: Grid<AlbumGridRow>,
    block: (@VaadinDsl PhotoDetails).() -> Unit = {}
) =
    init(PhotoDetails(photoStorage, albumGrid), block)