package vlite.digikamweb.ui.view.editor

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.router.*
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.core.ui.onSelected
import vlite.core.ui.removeDrawer
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.TenantName
import vlite.digikamweb.domain.services.storage.PhotoStorageA
import vlite.digikamweb.domain.services.storage.TenantStorageA
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import vlite.digikamweb.ui.base.view.BaseAppLayoutA
import vlite.digikamweb.ui.base.view.ViewRouteParameter
import vlite.digikamweb.ui.base.view.access.validate
import vlite.digikamweb.ui.view.editor.EditorView.Companion.ROUTE
import vlite.digikamweb.ui.view.editor.editalbums.EditAlbumsContentA
import vlite.digikamweb.ui.view.editor.editpersonphotos.EditPersonPhotoContentA
import vlite.digikamweb.ui.view.editor.editphoto.EditPhotoDrawerA
import java.util.*

@Suppress("unused")
@Route(ROUTE)
class EditorView(
    override val tenantStorage: TenantStorageA,
    override val editPhotoDrawer: EditPhotoDrawerA,
    override val editPersonPhotoContent: EditPersonPhotoContentA,
    override val editAlbumsContent: EditAlbumsContentA
) : BaseAppLayoutA(),
    BeforeEnterObserver,
    ViewRouteParameter.TenantNameValue.Holder,
    ViewRouteParameter.EditAccessCodeValue.Holder,
    EditPhotoDrawerA.Holder,
    EditPersonPhotoContentA.Holder,
    EditAlbumsContentA.Holder {

    companion object : KLoggerA {

        private val log by lazy { classLogger }

        private const val ROUTE =
            "/edit/${ViewRouteParameter.TenantNameValue.TEMPLATE}/${ViewRouteParameter.EditAccessCodeValue.TEMPLATE}"

        fun navigateToMe(tenantName: TenantName, editAccessCode: EditAccessCode) {
            UI.getCurrent().navigate(
                EditorView::class.java,
                RouteParam(TenantName.ATTRIBUTE_NAME, tenantName.value),
                RouteParam(EditAccessCode.ATTRIBUTE_NAME, editAccessCode.value)
            )
        }

    }

    private enum class I18n(
        override val en: String,
        override val ru: String,
        override val he: String
    ) : AppI18NProvider.ProvidedValuesA {

        EditPhoto(
            en = "Photo",
            ru = "Фото",
            he = "תַצלוּם"
        ),

        EditPersonPhoto(
            en = "Person photos",
            ru = "Фото людей",
            he = "תַצלוּם"
        ),

        EditAlbums(
            en = "Albums",
            ru = "Альбомы",
            he = "אלבומים"
        ),

    }

    override val tenantNameRouteParameterValue = ViewRouteParameter.TenantNameValue()
    override val editAccessCodeRouteParameterValue = ViewRouteParameter.EditAccessCodeValue()

    private var editPhotoTab: Tab? = null
    private var editPersonPhotoTab: Tab? = null
    private var editAlbumsTab: Tab? = null

    override fun localeChange(locale: Locale) {
        editPhotoTab?.label = I18n.EditPhoto.translation(locale)
        editPersonPhotoTab?.label = I18n.EditPersonPhoto.translation(locale)
        editAlbumsTab?.label = I18n.EditAlbums.translation(locale)
    }

    override fun beforeEnter(event: BeforeEnterEvent) {
        tenantNameRouteParameterValue.initFrom(event, log)
        editAccessCodeRouteParameterValue.initFrom(event, log)
    }

    override fun initAfterNavigation(event: AfterNavigationEvent) {
        tenantStorage.validate(log) {

            val photoStorage = tenantStorage.photoStorage(tenantNameRouteParameterValue())

            photoStorage.validate(log) {

                editAccessCodeRouteParameterValue().validate(log, photoStorage.editAccessCodeFromFile(log)!!) {

                    initAfterNavigation(photoStorage)

                }

            }

        }
    }

    private fun initAfterNavigation(photoStorage: PhotoStorageA) {

        navbar(touchOptimized = true) {
            drawerToggle()

            tabs {

                editPhotoTab = tab {
                    icon(VaadinIcon.EDIT)

                    onSelected {
                        editPhotoDrawer.run { populate(photoStorage) }
                    }
                }

                editPersonPhotoTab = tab {
                    icon(VaadinIcon.EDIT)

                    onSelected {
                        removeDrawer()
                        editPersonPhotoContent.run { populate(photoStorage) }
                    }
                }

                editAlbumsTab = tab {
                    icon(VaadinIcon.EDIT)

                    onSelected {
                        removeDrawer()
                        editAlbumsContent.run { populate(photoStorage) }
                    }
                }

            }
        }

    }

}