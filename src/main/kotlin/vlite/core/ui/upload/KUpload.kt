@file:Suppress("unused")

package vlite.core.ui.upload

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.upload.Receiver
import com.vaadin.flow.component.upload.Upload
import elemental.json.Json
import vlite.core.ui.button
import vlite.core.ui.i18n.KLocaleChangeObserverA
import vlite.digikamweb.ui.base.i18n.i18n
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.util.*

class KUpload(maxFileSize: Int, acceptedFileTypes: Array<out String>) : KComposite(),
    KLocaleChangeObserverA {

    private var uploadComponent: Upload? = null

    private var byteArrayOutputStream = ByteArrayOutputStream()
    private var fileName: String = ""
    private var mimeType: String = ""

    @Suppress("unused")
    private val root: Div = ui {
        div {
            horizontalLayout {
                isPadding = false

                uploadComponent = upload(object : Receiver {
                    override fun receiveUpload(fileName: String, mimeType: String): OutputStream {
                        this@KUpload.fileName = fileName
                        this@KUpload.mimeType = mimeType
                        return byteArrayOutputStream
                    }
                }) {

                    isDropAllowed = true
                    maxFiles = 1
                    isAutoUpload = true

                    this.maxFileSize = maxFileSize
                    this.setAcceptedFileTypes(*acceptedFileTypes)

                    addSucceededListener {
                        onFileReceived(
                            this@KUpload.fileName,
                            this@KUpload.mimeType,
                            byteArrayOutputStream.toByteArray()
                        )
                    }

                }

            }
        }
    }

    override fun localeChange(locale: Locale) {
        uploadComponent?.i18n(locale)
    }


    /**
     * [KUpload] API
     */
    var onFileReceived: (fileName: String, fileMimeType: String, fileContent: ByteArray) -> Unit = {_, _, _ ->}

    fun reset() {
        byteArrayOutputStream = ByteArrayOutputStream()
        fileName = ""
        mimeType = ""
    }

    fun clearUploadedFileList() {
        uploadComponent?.element?.setPropertyJson("files", Json.createArray())
    }

    fun button(block: (@VaadinDsl HasComponents).() -> Component) {
        uploadComponent!!.button(block)
        uploadComponent!!.isDropAllowed = false
        clearUploadedFileList()
    }

}

@VaadinDsl
fun (@VaadinDsl HasComponents).upload(
    maxFileSize: Int,
    vararg acceptedFileTypes: String,
    block: (@VaadinDsl KUpload).() -> Unit = {}
) =
    init(KUpload(maxFileSize, acceptedFileTypes), block)