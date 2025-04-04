package vlite.digikamweb.backend

import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import java.nio.file.Path

interface FileTenantStorageA : BaseFileStorageA {

    fun tenantDirectories(): Sequence<Path>
    fun tenantDirectory(dirName: String): Path

    /*
        interface Holder {
            val fileTenantStorage : FileTenantStorageA
        }
    */

}