package vlite.digikamweb.ui.view.editor.editpersonphotos

import com.github.mvysny.karibudsl.v10.buildLitRenderer
import com.github.mvysny.karibudsl.v10.column
import com.github.mvysny.karibudsl.v10.grid
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import kotlinx.css.em
import kotlinx.css.pct
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.ui.base.view.*

data class EditPersonPhotoGridRow(override var personPhoto: PersonPhoto) : PersonPhoto.Holder

fun VerticalLayout.editPersonPhotoGrid(
    onRowSelected: OnRowSelected<EditPersonPhotoGridRow>
) : Grid<EditPersonPhotoGridRow> {
    return grid<EditPersonPhotoGridRow> {

        defaultConfiguration(onRowSelected)

        column(
            buildLitRenderer<EditPersonPhotoGridRow> {

                val personPhotoPictureImage = personPhotoPictureImageProperty()
                val personPhotoName = personPhotoNameProperty()

                templateExpression {
                    imageRowLayout(10.em) {

                        renderPhotoPictureImage(personPhotoPictureImage, 90.pct)
                        renderPhotoName(personPhotoName)

                    }
                }

            }
        )

    }
}

/*
fun Grid<EditPersonPhotoGridRow>.refreshPhoto(personPhoto: PersonPhoto) {
    selectedRow.personPhoto = personPhoto
    refreshRow(selectedRow)
}
*/

