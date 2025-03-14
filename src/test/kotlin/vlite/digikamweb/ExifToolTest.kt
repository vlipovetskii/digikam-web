package vlite.digikamweb

import org.junit.jupiter.api.BeforeEach
import vlite.core.backend.KExifTool
import vlite.core.backend.KExifToolA
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.objects.photo.Photo
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.Path
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


/**
 * [A Guide to JUnit 5](https://www.baeldung.com/junit-5)
 * [JUnit 5 for Kotlin Developers](https://www.baeldung.com/kotlin/junit-5-kotlin)
 * [Test code using JUnit in JVM – tutorial](https://kotlinlang.org/docs/jvm-test-using-junit.html)
 */
class ExifToolTest {

    companion object {
        private const val DIGIKAM_PEOPLE_TAG_NAME = "RegionPersonDisplayName"
    }

    @BeforeEach
    fun init() {
        println("startup")
        Files.copy(Path("f9-start.jpeg_original"), Path("f9-start.jpeg"), StandardCopyOption.REPLACE_EXISTING)
    }

    @Test
    fun version_Test() {
        val exifTool: KExifToolA = KExifTool()

        assertEquals("12.76", exifTool.version())
    }

    @Test
    fun allXmpData_Test() {
        // f9-start.jpeg_original
        val exifTool: KExifToolA = KExifTool()

        val photoPath = Path("f9-start.jpeg")

        assertEquals("Region Person Display Name      : Falcon 9", exifTool.allXmpData(Path("f9-start.jpeg"))[1])
        assertContentEquals(listOf(PersonName("Falcon 9")), Photo(photoPath).personNames)

    }

    @Test
    fun addPersonName_Test() {
        val exifTool: KExifToolA = KExifTool()

        val photoPath = Path("f9-start.jpeg")

        /**
         * PRB: XMP Toolkit                     : XMP Core 4.4.0-Exiv2 can change to Image::ExifTool 12.76
         * WO: Exclude the line XMP Toolkit from comparison
         */

        assertEquals("Region Person Display Name      : Falcon 9", exifTool.allXmpData(Path("f9-start.jpeg"))[1])
        assertContentEquals(listOf(PersonName("Falcon 9")), Photo(photoPath).personNames)

        val testPersonName = PersonName("John Doe")

        exifTool.addXmpTagValue(photoPath, DIGIKAM_PEOPLE_TAG_NAME, testPersonName.value)

        assertEquals("Region Person Display Name      : Falcon 9, John Doe", exifTool.allXmpData(Path("f9-start.jpeg"))[1])
        assertContentEquals(listOf(PersonName("Falcon 9"), testPersonName), Photo(photoPath).personNames)

        exifTool.removeXmpTagValue(photoPath, DIGIKAM_PEOPLE_TAG_NAME, testPersonName.value)

        assertEquals("Region Person Display Name      : Falcon 9",exifTool.allXmpData(Path("f9-start.jpeg"))[1])
        assertContentEquals(listOf(PersonName("Falcon 9")), Photo(photoPath).personNames)

    }

}