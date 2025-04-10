package vlite.digikamweb.backend

import org.slf4j.Logger
import org.springframework.core.env.Environment
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.core.getRequiredProperty
import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import java.nio.file.Path
import kotlin.io.path.*

class FileTenantStorage(
    env: Environment
) : FileTenantStorageA,
    BaseFileStorageA.Implementation {

    companion object : KLoggerA {
        private val log by lazy { classLogger }
    }

    override val homePath: Path = Path.of(env.getRequiredProperty<String>("FileTenantStorage.homePath", log))

    override fun logErrorIfNotExists(log: Logger) {
        log.error("FileTenantStorage(`$homePath`) does not exist")
    }

/*
    private fun Path.validTenantPath() {
        require(parent == homePath) { "`$parent` != `$homePath`" }
    }
*/

    override fun initialize(editAccessCode: String) {
        homePath.createDirectory()
        editAccessCodeToFile(editAccessCode)
    }

    override fun delete() {
        homePath.deleteDirectory()
    }

    override fun tenantDirectories(): Sequence<Path> {
        return homePath
            .listDirectoryEntries()
            .asSequence()
            .filter { it.isDirectory() && !it.isHidden() }
            .sortedByDescending { it.name }
    }

    override fun tenantDirectory(dirName: String): Path = homePath.resolve(dirName)

}