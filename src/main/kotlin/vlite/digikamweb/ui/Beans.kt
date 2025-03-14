package vlite.digikamweb.ui

import org.springframework.context.support.BeanDefinitionDsl
import vlite.core.beanPrototype
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import vlite.digikamweb.ui.session.AppServiceInitListener
import vlite.digikamweb.ui.view.admin.edittenants.EditTenantsContent
import vlite.digikamweb.ui.view.editor.editalbums.EditAlbumsContent
import vlite.digikamweb.ui.view.editor.editpersonphotos.EditPersonPhotoContent
import vlite.digikamweb.ui.view.editor.editphoto.EditPhotoContent
import vlite.digikamweb.ui.view.editor.editphoto.EditPhotoDrawer
import vlite.digikamweb.ui.view.main.albums.AlbumContent
import vlite.digikamweb.ui.view.main.albums.AlbumDrawer
import vlite.digikamweb.ui.view.main.people.PeopleContent
import vlite.digikamweb.ui.view.main.people.PeopleDrawer

fun BeanDefinitionDsl.uiBaseBeans() {

    run {

        /**
         * bean { KCustomErrorHandler() }
         * A component required a single bean, but 2 were found:
         * 	- vlite.digikamweb.ui.base.exceptionhandlers.KCustomErrorHandler#0: defined in unknown location
         * 	- KCustomErrorHandler: defined in file [vlite/digikamweb/ui/base/exceptionhandlers/KCustomErrorHandler.class]
         * WO: Comment bean { KCustomErrorHandler() } and leave bean declared implicitly by @ControllerAdvice
         */
        bean { AppServiceInitListener(ref()) }
    }

    bean { AppI18NProvider() }
}

fun BeanDefinitionDsl.uiAlbumBeans() {
    beanPrototype { AlbumContent() }
    beanPrototype { AlbumDrawer(ref()) }
}

fun BeanDefinitionDsl.uiPeopleBeans() {
    beanPrototype { PeopleContent() }
    beanPrototype { PeopleDrawer(ref()) }
}

fun BeanDefinitionDsl.uiEditPhotoBeans() {
    beanPrototype { EditPhotoContent(ref()) }
    beanPrototype { EditPhotoDrawer(ref()) }
}

fun BeanDefinitionDsl.uiEditPersonPhotoBeans() {
    beanPrototype { EditPersonPhotoContent() }
}

fun BeanDefinitionDsl.uiEditAlbumsBeans() {
    beanPrototype { EditAlbumsContent() }
    // beanPrototype { EditAlbumsDrawer(ref()) }
}

fun BeanDefinitionDsl.uiEditTenantsBeans() {
    beanPrototype { EditTenantsContent() }
    // beanPrototype { EditAlbumsDrawer(ref()) }
}
