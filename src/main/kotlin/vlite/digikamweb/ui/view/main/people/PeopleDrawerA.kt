package vlite.digikamweb.ui.view.main.people

import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

interface PeopleDrawerA {

    fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA)

    interface Holder {
        val peopleDrawer : PeopleDrawerA
    }

}

