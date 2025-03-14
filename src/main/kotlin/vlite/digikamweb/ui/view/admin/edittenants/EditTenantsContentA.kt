package vlite.digikamweb.ui.view.admin.edittenants

import vlite.digikamweb.domain.services.storage.TenantStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

interface EditTenantsContentA {

    fun BaseAppLayoutA.populate(tenantStorage: TenantStorageA)

    interface Holder {
        val editTenantsContent : EditTenantsContentA
    }

}
