package vlite.digikamweb.domain.services.storage

import org.slf4j.Logger
import vlite.digikamweb.backend.FilePhotoStorage
import vlite.digikamweb.backend.FilePhotoStorageA
import vlite.digikamweb.backend.FileTenantStorageA
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.Tenant
import vlite.digikamweb.domain.objects.TenantName
import vlite.digikamweb.domain.services.PhotoFactoryA

class TenantStorage(
    override val fileStorage: FileTenantStorageA,
    override val photoFactory: PhotoFactoryA
) : TenantStorageA,
    BaseStorageA.Implementation<FileTenantStorageA>,
    PhotoFactoryA.Holder {

    override fun logErrorIfNotExists(log: Logger) {
        fileStorage.logErrorIfNotExists(log)
    }

    private fun tenant(tenantName: TenantName) =
        Tenant(fileStorage.tenantDirectory(tenantName.value))

    private fun filePhotoStorage(tenantName: TenantName) : FilePhotoStorageA {
        return FilePhotoStorage(tenant(tenantName).path)
    }

    override fun photoStorage(tenant: TenantName): PhotoStorageA {
        return PhotoStorage(filePhotoStorage(tenant), photoFactory)
    }

    override fun tenants(): Sequence<Tenant> {
        return fileStorage
            .tenantDirectories()
            .map { albumPath ->
                Tenant(albumPath)
            }
            .sortedByDescending { it.name.value }
    }

    override fun addTenant(tenantName: TenantName, editAccessCode: EditAccessCode) : Tenant {
        filePhotoStorage(tenantName).apply {
            addDirectory()
            addPeopleDirectory()
        }
        return tenant(tenantName)
    }

    override fun renameTenant(tenant: Tenant, newTenantName: TenantName) : Tenant {
        filePhotoStorage(tenant.name).renameDirectory(newTenantName.value)
        return tenant(newTenantName)
    }

    override fun removeTenant(tenant: Tenant) {
        filePhotoStorage(tenant.name).apply {
            removePeopleDirectory()
            deleteDirectory()
        }
    }

}