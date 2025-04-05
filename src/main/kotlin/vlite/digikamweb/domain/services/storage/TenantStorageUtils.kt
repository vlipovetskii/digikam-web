package vlite.digikamweb.domain.services.storage

import org.slf4j.Logger
import vlite.core.doOperationWithLogging

fun TenantStorageA.clean(log: Logger) {
    log.doOperationWithLogging(operationTag = "TenantStorageA.clean") {

        tenants().forEach {tenant ->
            removeTenant(log, tenant)
        }

    }
}