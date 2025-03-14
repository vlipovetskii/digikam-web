package vlite.digikamweb.ui.view.main.people

import com.github.mvysny.karibudsl.v10.buildLitRenderer
import com.github.mvysny.karibudsl.v10.column
import com.github.mvysny.karibudsl.v10.grid
import com.vaadin.flow.component.HasComponents
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.ui.base.view.*
import java.util.*

data class PeopleGridRow(override val photo: Photo) : Photo.Holder

fun HasComponents.peopleGrid(
    locale: Locale,
    getItems: () -> List<PeopleGridRow>
) {
    grid<PeopleGridRow> {

        setHeightFull()

        addAppThemeVariants()

        column(
            buildLitRenderer<PeopleGridRow> {

                val photoPictureImage = photoPictureImageProperty()
                val photoName = photoNameProperty()
                val photoDateTimeOriginal = photoDateTimeOriginalProperty(locale)
                val albumName = albumNameProperty()

                templateExpression {
                    imageRowLayout {

                        renderPhotoPictureImage(photoPictureImage)
                        renderPhotoName(photoName)
                        renderPhotoDateTimeOriginal(photoDateTimeOriginal)
                        renderAlbumName(albumName)

                    }
                }

            }
        )

        setItems(getItems())
    }
}