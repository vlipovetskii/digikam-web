package vlite.digikamweb.ui.view.editor.editphoto

import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

interface EditPhotoDrawerA {

    fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA)

    interface Holder {
        val editPhotoDrawer : EditPhotoDrawerA
    }

}
