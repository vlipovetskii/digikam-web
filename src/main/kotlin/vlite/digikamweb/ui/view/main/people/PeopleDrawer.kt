package vlite.digikamweb.ui.view.main.people

import com.github.mvysny.karibudsl.v10.tab
import com.github.mvysny.karibudsl.v10.tabs
import com.vaadin.flow.component.tabs.Tabs
import vlite.core.ui.closeDrawerOnMobileDevice
import vlite.core.ui.drawer
import vlite.core.ui.onSelected
import vlite.core.ui.removeDrawer
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.ui.base.image
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

class PeopleDrawer(
    override val peopleContent: PeopleContentA
) : PeopleDrawerA,
    PeopleContentA.Holder {

    override fun BaseAppLayoutA.populate(photoStorage: PhotoStorageA) {

        removeDrawer()
        drawer {

            tabs(Tabs.Orientation.VERTICAL) {

                photoStorage.allPersonNames().forEach { personName ->

                    tab(personName.value/*, VaadinIcon.FILE_TREE_SUB*/) {
                        image(photoStorage.personPhoto(personName))

                        onSelected {

                            peopleContent.run { populate(photoStorage, personName) }
                            closeDrawerOnMobileDevice()

                        }

                    }

                }

            }

        }

    }

}