package vlite.digikamweb.domain.services.storage

import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.Tenant
import vlite.digikamweb.domain.objects.TenantName

interface TenantStorageA : BaseStorageA {

    fun photoStorage(tenantName: TenantName): PhotoStorageA

    fun tenants(): Sequence<Tenant>

    fun addTenant(tenantName: TenantName, editAccessCode: EditAccessCode): Tenant
    fun renameTenant(tenant: Tenant, newTenantName: TenantName): Tenant
    fun removeTenant(tenant: Tenant)

    // fun allTenants(): Sequence<Tenant>

    interface Holder {
        val tenantStorage: TenantStorageA
    }

}