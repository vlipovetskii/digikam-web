package vlite.digikamweb.ui.view.main.albums

import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

interface AlbumDrawerA {

    fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA)

    interface Holder {
        val albumDrawer : AlbumDrawerA
    }

}

