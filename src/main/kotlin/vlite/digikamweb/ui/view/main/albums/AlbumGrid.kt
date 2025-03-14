package vlite.digikamweb.ui.view.main.albums

import com.github.mvysny.karibudsl.v10.buildLitRenderer
import com.github.mvysny.karibudsl.v10.column
import com.github.mvysny.karibudsl.v10.grid
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.ui.base.view.*
import java.util.*

data class AlbumGridRow(override val photo: Photo) : Photo.Holder

fun VerticalLayout.albumGrid(
    locale: Locale,
    onRowSelected: OnRowSelected<AlbumGridRow>,
) : Grid<AlbumGridRow> {
    return grid<AlbumGridRow> {

        defaultConfiguration(onRowSelected)

        column(
            buildLitRenderer<AlbumGridRow> {

                val photoPictureImage = photoPictureImageProperty()
                val photoName = photoNameProperty()
                val photoDateTimeOriginal = photoDateTimeOriginalProperty(locale)
                val personNames = personNamesProperty()

                // MP:RegionInfo/MPRI:Regions[1]/MPReg:PersonDisplayName

                templateExpression {
                    imageRowLayout {

                        renderPhotoPictureImage(photoPictureImage)
                        renderPhotoName(photoName)
                        renderPhotoDateTimeOriginal(photoDateTimeOriginal)
                        renderPersonNames(personNames)

                    }
                }

            }
        )


    }
}
