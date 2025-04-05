package vlite.digikamweb

import org.springframework.context.support.BeanDefinitionDsl
import vlite.digikamweb.backend.backendBeans
import vlite.digikamweb.domain.services.domainServicesBeans
import vlite.digikamweb.ui.*

fun BeanDefinitionDsl.appBeans() {
    uiBaseBeans()

    backendBeans()

    domainServicesBeans()

    run {
        uiAlbumBeans()
        uiPeopleBeans()
    }

    run {
        uiEditPhotoBeans()
        uiEditPersonPhotoBeans()
        uiEditAlbumsBeans()
        uiEditTenantsBeans()
    }
}
