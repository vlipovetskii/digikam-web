package vlite.digikamweb

import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.TenantName

object TestFixtures {
    val ADMIN_EDIT_ACCESS_CODE = EditAccessCode("1234")
    val TENANT_EDIT_ACCESS_CODE = EditAccessCode("12345")
    val TENANT_1_NAME = TenantName("1111")
    val TENANT_1_NAME_MODIFIED = TenantName("1112")
}