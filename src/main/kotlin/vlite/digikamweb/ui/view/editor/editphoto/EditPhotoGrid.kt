package vlite.digikamweb.ui.view.editor.editphoto

import com.github.mvysny.karibudsl.v10.buildLitRenderer
import com.github.mvysny.karibudsl.v10.column
import com.github.mvysny.karibudsl.v10.grid
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.ui.base.view.*
import java.util.*

data class EditPhotoGridRow(override var photo: Photo) : Photo.Holder

fun VerticalLayout.editPhotoGrid(
    locale: Locale,
    onRowSelected: OnRowSelected<EditPhotoGridRow>
) : Grid<EditPhotoGridRow> {
    return grid<EditPhotoGridRow> {

        defaultConfiguration(onRowSelected)

        column(
            buildLitRenderer<EditPhotoGridRow> {

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

