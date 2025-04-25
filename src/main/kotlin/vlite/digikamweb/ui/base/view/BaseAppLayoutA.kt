package vlite.digikamweb.ui.base.view

import vlite.core.ui.view.KAppLayoutA
import vlite.digikamweb.domain.services.storage.TenantStorageA

abstract class BaseAppLayoutA : KAppLayoutA(),
    TenantStorageA.Holder