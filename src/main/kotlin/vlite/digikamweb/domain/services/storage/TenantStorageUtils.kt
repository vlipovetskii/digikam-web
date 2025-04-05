package vlite.digikamweb.domain.services.storage

import org.slf4j.Logger
import vlite.core.doOperationWithLogging

@Suppress("unused")
fun TenantStorageA.removeAllTenants(log: Logger) {
    log.doOperationWithLogging(operationTag = "TenantStorageA.clean") {

        tenants().forEach {tenant ->
            removeTenant(log, tenant)
        }

    }
}