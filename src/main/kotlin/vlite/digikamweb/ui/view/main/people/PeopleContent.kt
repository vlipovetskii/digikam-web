package vlite.digikamweb.ui.view.main.people

import vlite.core.ui.content
import vlite.core.ui.removeContent
import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

class PeopleContent : PeopleContentA {

    override fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA, personName: PersonName) {

        removeContent()
        content {
            peopleGrid(appLayoutLocale) {
                photoStorage.photos(personName).map { PeopleGridRow(it) }.toList()
            }
        }

    }

}

