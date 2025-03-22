package vlite.core.ui

import com.vaadin.flow.server.InputStreamFactory
import com.vaadin.flow.server.StreamResource
import vlite.core.domain.objects.MimeType
import java.io.*
import java.nio.charset.Charset

// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * [createStreamResource] shorthand for convenience
 * See also comments on [StreamResource] constructor
 */
public fun createStreamResource(name: String, getStream: () -> InputStream): StreamResource =
    StreamResource(
        name,
        InputStreamFactory {
            return@InputStreamFactory getStream()
        }
    )

// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * [toStreamResource] shorthand for convenience
 *
 * @param contentType [MimeType]
 * @return [StreamResource] which reads the contents of this [ByteArray]
 *
 * Example of usage:
 * ```kotlin
 * image(imageBytes.toStreamResource(imageName, MimeType.JPEG), imageName)
 * ```
 */
public fun ByteArray.toStreamResource(name: String, contentType: MimeType? = null) = run {
    createStreamResource(name) { ByteArrayInputStream(this) }.apply {
        if (contentType != null) setContentType(contentType.toString())
    }
}

// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * [toStreamResource] shorthand for convenience
 *
 * @param contentType [MimeType]
 * @param bufferSize See [BufferedInputStream] size parameter
 * @return [StreamResource] which reads the contents of this [File]
 *
 * Example of usage:
 * ```kotlin
 * image(File("foo.jpeg").toStreamResource("image/jpeg"), MimeType.JPEG)
 * ```
 */
public fun File.toStreamResource(name: String? = null, contentType: MimeType? = null, bufferSize: Int? = null) = run {
    createStreamResource(
        name ?: this.name
    ) {
        FileInputStream(this).let {
            if (bufferSize != null) BufferedInputStream(
                it,
                bufferSize
            ) else BufferedInputStream(it)
        }
    }.apply {
        if (contentType != null) setContentType(contentType.toString())
    }
}

// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * [toStreamResource] shorthand for convenience
 *
 * @param contentType [MimeType]
 * @return [StreamResource] which reads the contents of this [String]
 *
 * Example of usage:
 * ```kotlin
 * "foo".toStreamResource("foo-name", MimeType.TEXT_PLAIN)
 * ```
 */
public fun String.toStreamResource(name: String, contentType: MimeType? = null, charset: Charset = Charsets.UTF_8) = run {
    createStreamResource(name) { this.byteInputStream(charset) }.apply {
        if (contentType != null) setContentType(contentType.toString())
    }
}
