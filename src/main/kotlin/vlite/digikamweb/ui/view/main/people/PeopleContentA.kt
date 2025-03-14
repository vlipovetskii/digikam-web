package vlite.digikamweb.ui.view.main.people

import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

interface PeopleContentA {

    fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA, personName: PersonName)

    interface Holder {
        val peopleContent : PeopleContentA
    }

}
