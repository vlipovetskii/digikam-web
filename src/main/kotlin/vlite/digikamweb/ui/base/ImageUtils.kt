package vlite.digikamweb.ui.base

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.css
import com.github.mvysny.karibudsl.v10.icon
import com.github.mvysny.karibudsl.v10.image
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.icon.VaadinIcon
import kotlinx.css.*
import vlite.core.ui.resizeAndKeepAspectRatio
import vlite.core.ui.toStreamResource
import vlite.digikamweb.domain.objects.base.BasePhotoA
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.objects.photo.Photo

@VaadinDsl
fun (@VaadinDsl HasComponents).image(photo: PersonPhoto?) : Component {

    fun CssBuilder.setSize() {
        this.width = 2.em
        this.height = 2.em
    }

    return if (photo?.exists == true)
        image(photo.imageData.toStreamResource(photo.name.value, BasePhotoA.MIME_TYPE), photo.name.value) {
            css {
                setSize()
            }
        }
    else
        icon(VaadinIcon.USER) {
            css {
                setSize()
            }
        }
}

@VaadinDsl
fun (@VaadinDsl HasComponents).image(photo: Photo) {

    image(photo.imageData.toStreamResource(photo.name.value, BasePhotoA.MIME_TYPE), photo.name.value) {
        css {
            resizeAndKeepAspectRatio(); alignSelf = Align.center
        }
    }
}