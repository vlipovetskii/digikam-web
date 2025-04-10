package vlite.digikamweb.unit

import io.mockk.every
import io.mockk.mockk
import org.hibernate.validator.internal.util.Contracts.assertTrue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.env.Environment
import vlite.digikamweb.backend.FileTenantStorage
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.name
import kotlin.test.expect

open class FileTenantStorageTest {

    private lateinit var tempDir: Path
    private lateinit var env: Environment
    private lateinit var fileTenantStorage: FileTenantStorage

    @BeforeEach
    fun setUp() {
        tempDir = Files.createTempDirectory("fileTenantStorageTest")

        env = mockk()
        every { env.getRequiredProperty<String>("FileTenantStorage.homePath", any()) } returns tempDir.toString()

        fileTenantStorage = FileTenantStorage(env)
    }

    @AfterEach
    fun tearDown() {
        tempDir.toFile().deleteRecursively()
    }
    @Test
    fun `tenantDirectory should resolve correct subdirectory path`() {
        val dirName = "tenant1"
        val expected = tempDir.resolve(dirName)
        val actual = fileTenantStorage.tenantDirectory(dirName)
        expect(expected) { actual }
    }

    @Test
    fun `tenantDirectories should return only visible directories sorted by name descending`() {
        val names = listOf("tenantA", "tenantC", "tenantB", ".hidden")
        val expected = listOf("tenantC", "tenantB", "tenantA")

        // Create directories
        names.forEach {
            val dir = tempDir.resolve(it)
            dir.createDirectory()
        }

        val result = fileTenantStorage.tenantDirectories().map { it.name }.toList()
        expect(expected) { result }
    }

    @Test
    fun `tenantDirectories should return empty sequence if no directories exist`() {
        assertTrue(fileTenantStorage.tenantDirectories().none(), "tenantDirectories")
    }
}
