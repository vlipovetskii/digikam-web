package vlite.digikamweb.ui.base.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.icon.VaadinIcon
import kotlinx.css.*
import vlite.core.ui.resizeAndKeepAspectRatio
import vlite.digikamweb.domain.objects.photo.PersonPhoto
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.ui.controllers.ImageController
import java.util.*

fun <TGridRow : Photo.Holder> KLitRendererBuilder<TGridRow>.photoPictureImageProperty(): KLitRendererBuilder.Property<TGridRow> {
    val photoPictureImage by property { row ->
        // row.photo.imageDataAsBase64
        ImageController.urlPath(row.photo)
    }
    return photoPictureImage
}

fun <TGridRow : Photo.Holder> KLitRendererBuilder<TGridRow>.photoNameProperty(): KLitRendererBuilder.Property<TGridRow> {
    val photoName by property { row ->
        row.photo.name.value
    }
    return photoName
}

fun <TGridRow : Photo.Holder> KLitRendererBuilder<TGridRow>.photoDateTimeOriginalProperty(locale: Locale): KLitRendererBuilder.Property<TGridRow> {
    val photoDateTimeOriginal by property { row ->
        row.photo.dateTimeOriginal(locale)
    }
    return photoDateTimeOriginal
}

fun <TGridRow : Photo.Holder> KLitRendererBuilder<TGridRow>.albumNameProperty(): KLitRendererBuilder.Property<TGridRow> {
    val albumName by property { row ->
        row.photo.album.name.value
    }
    return albumName
}

fun <TGridRow : Photo.Holder> KLitRendererBuilder<TGridRow>.personNamesProperty(): KLitRendererBuilder.Property<TGridRow> {
    val personNames by property { row ->
        row.photo.personNamesString
    }
    return personNames
}

fun <TGridRow : PersonPhoto.Holder> KLitRendererBuilder<TGridRow>.personPhotoPictureImageProperty(): KLitRendererBuilder.Property<TGridRow> {
    val personPhotoPictureImage by property { row ->
        // row.photo.imageDataAsBase64
        ImageController.urlPath(row.personPhoto)
    }
    return personPhotoPictureImage
}

fun <TGridRow : PersonPhoto.Holder> KLitRendererBuilder<TGridRow>.personPhotoNameProperty(): KLitRendererBuilder.Property<TGridRow> {
    val personPhotoName by property { row ->
        row.personPhoto.name.value
    }
    return personPhotoName
}


fun <TGridRow> KLitRendererTagsBuilder<TGridRow>.imageRowLayout(height: LinearDimension = 30.em, block: KLitRendererTagsBuilder<TGridRow>.() -> Unit) {
    verticalLayout({
        // style { alignItems = Align.start; lineHeight = +KLumoLineHeight.XS; /* height = 100.pct; wrapText()  */ }
        // TODO How to configure row height = grid height ?
        style { alignItems = Align.start; this.height = height }
    }, block)
}

fun <TGridRow> KLitRendererTagsBuilder<TGridRow>.renderPhotoPictureImage(property: KLitRendererBuilder.Property<TGridRow>, maxHeight: LinearDimension = 80.pct) {
    horizontalLayout({
        // style { alignItems = Align.start; this.minHeight = 100.pct }
        style { alignItems = Align.start; this.maxHeight = maxHeight }
        theme(spacing)
    }) {
        img {
            style { resizeAndKeepAspectRatio(); alignSelf = Align.center }
            src(property)
        }
    }
}

fun <TGridRow> KLitRendererTagsBuilder<TGridRow>.renderPhotoName(property: KLitRendererBuilder.Property<TGridRow>) {
    span({
        style { color = Color.blue; fontWeight = FontWeight.bold; }
    }) {
        +property
    }
}

fun <TGridRow> KLitRendererTagsBuilder<TGridRow>.renderPhotoDateTimeOriginal(property: KLitRendererBuilder.Property<TGridRow>) {
    span {
        +property
    }
}

fun <TGridRow> KLitRendererTagsBuilder<TGridRow>.renderPersonNames(property: KLitRendererBuilder.Property<TGridRow>) {
    span({
        style { color = Color.blue; }
    }) {
        +property
    }
}

fun <TGridRow> KLitRendererTagsBuilder<TGridRow>.renderAlbumName(property: KLitRendererBuilder.Property<TGridRow>) {
    span({
        style { color = Color.blue; }
    }) {
        +property
    }
}

fun <TGridRow : Photo.Holder> KLitRendererBuilder<TGridRow>.renamePhotoButtonClick(action: () -> Unit): KLitRendererBuilder.Function<TGridRow> {
    val renamePhotoButtonClick by function { category ->
        action()
    }
    return renamePhotoButtonClick
}

fun <TGridRow> KLitRendererTagsBuilder<TGridRow>.renderRenamePhotoButton(
    property: KLitRendererBuilder.Property<TGridRow>,
    onButtonClick: KLitRendererBuilder.Function<TGridRow>
) {
    button({
        themeVariant(ButtonVariant.LUMO_TERTIARY)
        click(onButtonClick)
    }) {
        icon({ icon(VaadinIcon.EDIT.create()) })
        +property
    }

}
