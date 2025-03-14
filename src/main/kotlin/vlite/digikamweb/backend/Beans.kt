package vlite.digikamweb.backend

import org.springframework.context.support.BeanDefinitionDsl
import vlite.core.backend.KExifTool

fun BeanDefinitionDsl.backendBeans() {

    bean { FileTenantStorage(ref()) }
    // FilePhotoStorage is created by FileTenantStorage.photoStorage
    // bean { FilePhotoStorage(ref()) }

    bean { KExifTool(isOverwriteOriginal = true) }

}