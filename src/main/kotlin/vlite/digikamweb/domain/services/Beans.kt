package vlite.digikamweb.domain.services

import org.springframework.context.support.BeanDefinitionDsl
import vlite.digikamweb.domain.services.storage.TenantStorage

fun BeanDefinitionDsl.domainServicesBeans() {
    // PhotoStorage is created by TenantStorage.photoStorage
    // bean { PhotoStorage(ref()) }

    bean { PhotoFactory.WithMemoryCache() }
    bean { TenantStorage(ref(), ref()) }
    bean { PhotoPersonMetadata(ref(), ref()) }
}