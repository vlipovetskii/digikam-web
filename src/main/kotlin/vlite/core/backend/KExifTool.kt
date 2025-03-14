package vlite.core.backend

import vlite.core.KLoggerA
import vlite.core.classLogger
import java.nio.file.Path

class EditPhotoMetadataException(message: String = "") : Exception(message)

/**
 * [ExifTool by Phil Harvey](https://exiftool.org/)
 * [exiftool Application Documentation](https://exiftool.org/exiftool_pod.html)
 * ```
 * SYNOPSIS
 * Reading
 * exiftool [OPTIONS] [-TAG...] [--TAG...] FILE...
 *
 * Writing
 * exiftool [OPTIONS] -TAG[+-<]=[VALUE]... FILE...
 * ```
 * [ExifTool Command-Line Examples] (https://exiftool.org/examples.html)
 */
class KExifTool(
    /**
     * [-overwrite_original](https://www.exiftool.org/exiftool_pod.html#overwrite_original)
     * [-overwrite_original_in_place](https://www.exiftool.org/exiftool_pod.html#overwrite_original)
     */
    private val isOverwriteOriginal: Boolean = false
) : KExifToolA {

    companion object : KLoggerA {
        private val log by lazy { classLogger }

        /**
         * -a, --a (-duplicates, --duplicates)
         * Allow (-a) or suppress (--a) duplicate tag names to be extracted.
         * By default, duplicate tags are suppressed
         * when reading unless the -ee or -X options are used or the Duplicates option is enabled in the configuration file.
         * When writing, this option allows multiple Warning messages to be shown.
         * Duplicate tags are always extracted when copying.
         */
        @Suppress("unused")
        private const val OPTIONS = ""
    }

    private fun execAndGetResults(vararg options: String): List<String> {
        val processExecutionResult = ProcessBuilder().execAndGetResults(
            log,
            outputStreamToLog = true,
            maskSensitiveData = { it },
            "exiftool",
            *options
        )
        if (processExecutionResult.processExitCode != 0) throw EditPhotoMetadataException()
        return processExecutionResult.processOutput
    }


    /**
     * (OPTIONS)[https://exiftool.org/exiftool_pod.html#OPTIONS]
     * ```
     * -ver Print exiftool version number
     * ```
     */
    override fun version() = execAndGetResults("-ver").joinToString()

    /**
     * [ExifTool Command-Line Examples] (https://exiftool.org/examples.html)
     * ```
     * 16) undefined
     *      exiftool -xmp:author:all -a image.jpg
     * Extract all author-related XMP information from an image.
     * ```
     */
    override fun allXmpData(photoPath: Path) = execAndGetResults("-xmp:all", "-a", photoPath.toString())

    /**
     * [17. "List-type tags do not behave as expected"](https://exiftool.org/faq.html#Q17)
     * ```
     * Tags indicated by a plus sign (+) in the tag name documentation are list-type tags. Two examples of common list-type tags are IPTC:Keywords and XMP:Subject. These tags may contain multiple items which are combined into a single string when reading. (By default, extracted list items are separated by a comma and a space, but the -sep option may be used to change this.) When writing, separate items are assigned individually. For example, the following command writes three keywords to all writable files in directory DIR, replacing any previously existing keywords:
     *      exiftool -keywords=one -keywords=two -keywords=three DIR
     *
     * To prevent duplication when adding new items, specific items can be deleted then added back again in the same command. For example, the following command adds the keywords "one" and "two", ensuring that they are not duplicated if they already existed in the keywords of an image:
     *      exiftool -keywords-=one -keywords+=one -keywords-=two -keywords+=two DIR
     *
     * Note that as with "=" in the first three examples above, the "<" operation of this command overwrites any Keywords that existed previously in the original file. To add to or remove from the existing keywords, use "+<" or "-<".
     * ```
     *
     * [exiftool Application Documentation](https://exiftool.org/exiftool_pod.html)
     * ```
     * Writing
     * exiftool [OPTIONS] -TAG[+-<]=[VALUE]... FILE...
     * ```
     * [op]: +-<
     *
     */
    private fun writeXmpTagValue(picturePath: Path, tagName: String, op: String, value: String, vararg otherOptions: String) {

        /**
         *             *arrayOf(
         *                 "-overwrite_original".takeIf { isOverwriteOriginal }.orEmpty(),
         *                 "-XMP:$tagName${op}=$value",
         *                 picturePath.toString()
         *             )
         *
         * PRB: Error: Zero-length file name - ""
         * Cause: empty parameter is interpreted like empty Zero-length file name
         * WO: Exclude missing options
         */
        val options = buildList {
            if (isOverwriteOriginal) add("-overwrite_original")
            otherOptions.forEach {
                add(it)
            }
            add("-XMP:$tagName${op}=$value")
            add(picturePath.toString())
        }
        execAndGetResults(*options.toTypedArray())
    }

    override fun addXmpTagValue(picturePath: Path, tagName: String, value: String, vararg otherOptions: String) =
        writeXmpTagValue(picturePath, tagName, "+", value, *otherOptions)

    override fun removeXmpTagValue(picturePath: Path, tagName: String, value: String, vararg otherOptions: String) =
        writeXmpTagValue(picturePath, tagName, "-", value, *otherOptions)

    override fun removeXmpTag(picturePath: Path, tagName: String, vararg otherOptions: String) =
        writeXmpTagValue(picturePath, "", tagName, "", *otherOptions)

}