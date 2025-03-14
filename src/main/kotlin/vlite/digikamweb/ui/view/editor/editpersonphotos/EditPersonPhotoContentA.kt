package vlite.digikamweb.ui.view.editor.editpersonphotos

import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

interface EditPersonPhotoContentA {

    fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA)

    interface Holder {
        val editPersonPhotoContent : EditPersonPhotoContentA
    }

}
