package vlite.core.ui

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.vaadin.flow.server.InputStreamFactory
import com.vaadin.flow.server.StreamResource
import java.io.ByteArrayInputStream
import java.io.InputStream

// TODO PR
/**
 * [toStreamResource] shorthand for convenience
 * See also comments on [StreamResource] constructor
 *
 * Example of usage:
 * ```kotlin
 * image(StreamResource("foo.txt", InputStreamFactory { "foo".byteInputStream() }))
 * to
 * image("foo.txt".createStreamResource { "foo".byteInputStream() })
 * ```
 */
@VaadinDsl
public fun String.createStreamResource(getStream: () -> InputStream) =
    StreamResource(
        this,
        InputStreamFactory {
            return@InputStreamFactory getStream()
        }
    )

// TODO PR
@VaadinDsl
/**
 * [toStreamResource] shorthand for convenience
 *
 * @param contentType See comments on [StreamResource.setContentType], e.g. mime type like "image/jpeg"
 *
 * Example of usage:
 * ```kotlin
 * image(imageData.toStreamResource(imageName, "image/jpeg"), imageName)
 * ```
 */
public fun ByteArray.toStreamResource(name: String, contentType: String) = run {
    name.createStreamResource { ByteArrayInputStream(this) }.apply {
        setContentType(contentType)
    }
}
