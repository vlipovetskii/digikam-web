package vlite.digikamweb.ui.view.editor.editphoto

import vlite.digikamweb.domain.objects.Album
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

interface EditPhotoContentA {

    fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA, album: Album)

    interface Holder {
        val editPhotoContent : EditPhotoContentA
    }

}
