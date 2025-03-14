package vlite.core.ui.clipboard

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.init
import com.vaadin.flow.component.*
import com.vaadin.flow.shared.Registration
import vlite.core.domain.services.KImageFormat
import vlite.core.ui.clipboard.KClipboardImageReader.ClipboardImagePastedEvent
import java.util.*
import kotlin.enums.EnumEntries

/**
 * [KClipboardImageReader] is based on the code provided by ChatGPT
 *
 */
@Tag("div")
class KClipboardImageReader(
    private val imageFormats: EnumEntries<KImageFormat> = KImageFormat.ALL
) : Component() {

    // item.types.includes('image/png') || item.types.includes('image/jpeg')
    private val itemTypesIncludes get() = imageFormats.joinToString(separator = " || ") {
        "item.types.includes('image/${it.name}')"
    }

    /**
     * [executeClipboardRead] requires 'Allowed to see your clipboard' permission in web browsers.
     * See [How do I set clipboard permissions for different browsers?](https://updraftplus.com/faqs/how-do-i-set-clipboard-permissions-for-different-browsers/)
     * See [Allow clipboard permissions for different browsers](https://help.joomag.com/knowledge/clipboard-permissions)
     * [Chrome](chrome://settings/content/clipboard)
     */
    fun executeClipboardRead() {

        element.executeJs(
            """
            const component = this;
            navigator.clipboard.read().then(items => {
                for (let item of items) {
                    if ($itemTypesIncludes) {
                        item.getType(item.types[0]).then(blob => {
                            let reader = new FileReader();
                            reader.onload = (e) => {
                                component.dispatchEvent(new CustomEvent('clipboard-image-pasted', {detail: e.target.result.split(',')[1]}));
                            };
                            reader.readAsDataURL(blob);
                        });
                    }
                }
            }).catch(err => console.error(err));
            """
        )
    }

    @DomEvent("clipboard-image-pasted")
    class ClipboardImagePastedEvent(
        source: KClipboardImageReader,
        fromClient: Boolean,
        @EventData("event.detail") base64Data: String
    ) : ComponentEvent<KClipboardImageReader>(source, fromClient) {
        val imageBytes: ByteArray = Base64.getDecoder().decode(base64Data)
    }

    fun addClipboardImagePastedListener(listener: ComponentEventListener<ClipboardImagePastedEvent>): Registration
        = addListener(ClipboardImagePastedEvent::class.java, listener)

}

@VaadinDsl
fun (@VaadinDsl HasComponents).clipboardImageReader(
    imageFormats: EnumEntries<KImageFormat> = KImageFormat.ALL,
    listener: ComponentEventListener<ClipboardImagePastedEvent>,
) =
    init(KClipboardImageReader(imageFormats)) {
        addClipboardImagePastedListener(listener)
    }