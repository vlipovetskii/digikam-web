package vlite.digikamweb.ui.view.editor.components

import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.karibudsl.v23.popover
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.Scroller
import com.vaadin.flow.component.popover.PopoverPosition
import com.vaadin.flow.component.popover.PopoverVariant
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import kotlinx.css.Color
import kotlinx.css.color
import kotlinx.css.em
import kotlinx.css.width
import vlite.core.domain.services.toFormat
import vlite.core.ui.clipboard.clipboardImageReader
import vlite.core.ui.content
import vlite.core.ui.upload.upload
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.base.BasePhotoA
import vlite.digikamweb.domain.objects.photo.Photo
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.dialogs.openEditItemTextDialog
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import vlite.digikamweb.ui.base.image
import vlite.digikamweb.ui.base.view.SideComponentA
import vlite.digikamweb.ui.view.editor.editphoto.EditPhotoGridRow

/**
 * [Creating a Component Using Existing Components](https://vaadin.com/docs/latest/flow/create-ui/creating-components/composite)
 * [Toolbar](https://github.com/mvysny/karibu-dsl/blob/master/example/src/main/kotlin/com/vaadin/starter/beveragebuddy/ui/Toolbar.kt)
 *
 *
 */
class PhotoDetailsEditor(
    override val photoStorage: PhotoStorageA,
    private val selectedRow: () -> EditPhotoGridRow,
    private val updateInPhoto: PersonName.(add: Boolean) -> Unit,
    private val renamePersonName: (oldPersonName: PersonName, newPersonName: PersonName) -> Int
) : SideComponentA(),
    PhotoStorageA.Holder {

    companion object {
        private val WIDTH = 25.em
    }

    private enum class I18n(
        override val en: String,
        override val ru: String,
        override val he: String
    ) : AppI18NProvider.ProvidedValuesA {

        SavePersonNameToPhotoErrorMessage(
            en = "Save person name “%s” to photo error",
            ru = "Ошибка при сохранении имени человека “%s” в фото",
            he = "ששמור שם אדם “%s” בשגיאת תמונה"
        ),

        RenamePersonName(
            en = "Rename person name",
            ru = "Переименовать имя человека",
            he = "שנה את שם האדם"
        ),

        ClipboardPastePermissionRequired(
            en = "Allow to see your clipboard📋",
            ru = "Разрешить просмотр вашего буфера обмена📋",
            he = "אפשר לראות את הלוח שלךם📋"
        ),

    }

    lateinit var personNameTextField: TextField

    /**
     * [Scroller](https://vaadin.com/docs/latest/components/scroller)
     */
    lateinit var personNameScroller: Scroller
    val personNameCheckBoxMap = mutableMapOf<PersonName, Checkbox>()

    lateinit var addNewPersonNameButton: Button

    fun populatePersonNames() {

        personNameScroller.content {
            div {

                personNameCheckBoxMap.clear()

                val selectedPersonNames = selectedRow().photo.personNames

                photoStorage.allPersonNames().forEach { personName ->
                    horizontalLayout(padding = false) {

                        css {
                            width = WIDTH
                        }

                        personNameCheckBoxMap[personName] = checkBox {
                            value = personName in selectedPersonNames
                            addValueChangeListener {
                                personName.updateInPhoto(value)
                            }
                        }
                        uploadImage(personName)
                        renameButton(personName)
                    }
                }
            }
        }

    }

    private fun HorizontalLayout.uploadImage(
        personName: PersonName
    ) {
        val upload = upload(
            maxFileSize = Photo.MAX_SIZE,
            BasePhotoA.MIME_TYPE,
        ) {

            uploadButton { image(photoStorage.personPhoto(personName)) }

            onFileReceived = { fileName, fileMimeType, fileContent ->
                // "`$fileName`, `$fileMimeType`, `${fileContent.size}`".notifyInfo()

                val newPersonPhoto = photoStorage.addPersonPhoto(
                    personName,
                    fileContent
                )
                uploadButton { image(newPersonPhoto) }
            }

        }

        val readImageFromClipboard = clipboardImageReader { event ->

            val newPersonPhoto = photoStorage.addPersonPhoto(
                personName,
                event.imageBytes.toFormat()
            )
            upload.uploadButton { image(newPersonPhoto) }
        }

        icon(VaadinIcon.PASTE) {
            css {
                this.color = Color.blue
            }
            onClick {
                readImageFromClipboard.executeClipboardRead()
            }

            /**
             * [Popover](https://vaadin.com/docs/latest/components/popover)
             */
            popover(PopoverPosition.BOTTOM) {

                addThemeVariants(PopoverVariant.ARROW, PopoverVariant.LUMO_NO_PADDING)

                isOpenOnClick = false
                isOpenOnFocus = false

                isOpenOnHover = true

                verticalLayout {

                    h3(I18n.ClipboardPastePermissionRequired.translation(locale))

                    /**
                     * anchor(
                     *   href = "chrome://settings/content/clipboard",
                     *   text = "Google Chrome"
                     * )
                     * PRB: the link is blocked by browser
                     * WO:
                     */
                    anchor(
                        href = "https://help.joomag.com/knowledge/clipboard-permissions",
                        text = "Allow clipboard permissions for different browsers"
                    )
                    anchor(
                        href = "https://updraftplus.com/faqs/how-do-i-set-clipboard-permissions-for-different-browsers/",
                        text = "How do I set clipboard permissions for different browsers?"
                    )

                }


            }
        }

    }

    private fun HorizontalLayout.renameButton(
        personName: PersonName
    ) {
        button(personName.value, VaadinIcon.EDIT.create()) {
            onClick {
                I18n.RenamePersonName.translation(locale)
                    .openEditItemTextDialog(
                        locale,
                        personName.value,
                        isValid = { PersonName(it).isValid },
                        errorMessage = I18n.SavePersonNameToPhotoErrorMessage,
                    ) {
                        renamePersonName(
                            personName,
                            PersonName(it)
                        )

                    }
            }
        }
    }

    @Suppress("unused")
    private val root: Div = ui {
        div {

            verticalLayout(padding = false) {
                setHeightFull()

                horizontalLayout {
                    isPadding = false

                    personNameTextField = textField("People") {

                        css {
                            width = WIDTH
                        }

                        prefixComponent = VaadinIcon.SEARCH.create()

                        valueChangeMode = ValueChangeMode.EAGER
                        addValueChangeListener {

                            if (it.isFromClient) {
                                addNewPersonNameButton.isEnabled =
                                    PersonName(value).let { it.isValid && it !in personNameCheckBoxMap }
                            }
                        }

                    }

                    addNewPersonNameButton = iconButton(VaadinIcon.PLUS.create()) {

                        isEnabled = false

                        onClick {

                            PersonName(personNameTextField.value).updateInPhoto(true)
                            personNameTextField.value = ""
                            populatePersonNames()
                        }
                    }

                }

                personNameScroller = scroller(Scroller.ScrollDirection.VERTICAL)

            }
        }
    }

}

@VaadinDsl
fun (@VaadinDsl HasComponents).photoDetailsEditor(
    photoStorage: PhotoStorageA,
    selectedRow: () -> EditPhotoGridRow,
    updateInPhoto: PersonName.(add: Boolean) -> Unit,
    renamePersonName: (oldPersonName: PersonName, newPersonName: PersonName) -> Int,
) =
    init(PhotoDetailsEditor(photoStorage, selectedRow, updateInPhoto, renamePersonName))