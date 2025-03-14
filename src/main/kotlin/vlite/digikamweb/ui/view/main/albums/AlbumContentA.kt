package vlite.digikamweb.ui.view.main.albums

import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

interface AlbumContentA {

    fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA, album: Album)

    interface Holder {
        val albumContent : AlbumContentA
    }

}

