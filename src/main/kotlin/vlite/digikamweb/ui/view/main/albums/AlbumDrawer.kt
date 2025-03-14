package vlite.digikamweb.ui.view.main.albums

import com.github.mvysny.karibudsl.v10.tab
import com.github.mvysny.karibudsl.v10.tabs
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.tabs.Tabs
import vlite.core.ui.closeDrawerOnMobileDevice
import vlite.core.ui.drawer
import vlite.core.ui.onSelected
import vlite.core.ui.removeDrawer
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

class AlbumDrawer(
    override val albumContent: AlbumContentA
) : AlbumDrawerA,
    AlbumContentA.Holder {

    override fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA) {

        removeDrawer()
        drawer {

/*
            onSelectedTabs(Tabs.Orientation.VERTICAL) {

                photoStorage.albums().forEach { album ->

                    tab(album.name.value, VaadinIcon.FILE_TREE_SUB) {

                        onSelected {

                            albumContent.run { populate(photoStorage, album) }
                            closeDrawerOnMobileDevice()

                        }

                    }

                }

            }
*/

            tabs(Tabs.Orientation.VERTICAL) {

                photoStorage.albums().forEach { album ->

                    tab(album.name.value, VaadinIcon.FILE_TREE_SUB) {

                        onSelected {

                            albumContent.run { populate(photoStorage, album) }
                            closeDrawerOnMobileDevice()

                        }

                    }

                }

            }

        }

    }

}