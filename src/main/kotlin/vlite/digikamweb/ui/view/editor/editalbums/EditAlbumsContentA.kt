package vlite.digikamweb.ui.view.editor.editalbums

import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

interface EditAlbumsContentA {

    fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA)

    interface Holder {
        val editAlbumsContent : EditAlbumsContentA
    }

}
