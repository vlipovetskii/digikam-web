package vlite.digikamweb.domain.services.storage

import org.slf4j.Logger
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.Tenant
import vlite.digikamweb.domain.objects.TenantName

interface TenantStorageA : BaseStorageA {

    fun photoStorage(tenantName: TenantName): PhotoStorageA

    fun tenants(): Sequence<Tenant>

    fun addTenant(log: Logger, tenantName: TenantName, editAccessCode: EditAccessCode = EditAccessCode.DEFAULT): Tenant
    fun renameTenant(log: Logger, tenant: Tenant, newTenantName: TenantName): Tenant
    fun removeTenant(log: Logger, tenant: Tenant)

    // fun allTenants(): Sequence<Tenant>

    fun initialize(log: Logger, editAccessCode: EditAccessCode)
    fun delete(log: Logger, )

    interface Holder {
        val tenantStorage: TenantStorageA
    }

}