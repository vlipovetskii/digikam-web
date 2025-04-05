package vlite.digikamweb.ui.view.admin.edittenants

import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.menuBar
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.FlexComponent
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.core.ui.configureWithFlexGrow
import vlite.core.ui.content
import vlite.core.ui.removeContent
import vlite.core.ui.selectedItem
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.Tenant
import vlite.digikamweb.domain.services.storage.TenantStorageA
import vlite.digikamweb.ui.base.view.BaseAppLayoutA

class EditTenantsContent(
) : EditTenantsContentA {

    private lateinit var editTenantsGrid: Grid<EditTenantsGridRow>

    private lateinit var addTenantMenuItem: MenuItem
    private lateinit var renameTenantMenuItem: MenuItem
    private lateinit var deleteTenantMenuItem: MenuItem
    private lateinit var changeEditAccessCodeMenuItem: MenuItem

    companion object : KLoggerA {
        private val log by lazy { classLogger }
    }

    override fun BaseAppLayoutA.populate(tenantStorage: TenantStorageA) {

        removeContent()
        content {

            horizontalLayout {

                alignItems = FlexComponent.Alignment.START

                isPadding = false
                setHeightFull()

                verticalLayout {

                    configureWithFlexGrow(5.0)

                    fun gridRows() = tenantStorage.tenants().map {
                        EditTenantsGridRow(it, tenantStorage.photoStorage(it.name).editAccessCodeFromFile())
                    }.toList()

                    fun refreshGridRows(newTenant: Tenant? = null) {
                        val gridRows = gridRows()

                        editTenantsGrid.setItems(gridRows)
                        // editorTenantGrid.refresh() is not required
                        if (newTenant != null) {
                            gridRows.first { row -> row.tenant == newTenant }.also { newRow ->
                                editTenantsGrid.scrollToItem(newRow)
                                editTenantsGrid.select(newRow)
                            }
                        }

                    }

                    horizontalLayout {
                        //isPadding = false

                        menuBar {
                            addTenantMenuItem = addTenantMenuItem(appLayoutLocale) { newTenantName ->
                                refreshGridRows(tenantStorage.addTenant(log, newTenantName, EditAccessCode("")))
                            }

                            // TODO display renameTenantButton inside GridRow close to picture
                            renameTenantMenuItem = renameTenantMenuItem(
                                appLayoutLocale,
                                selectedRow = { editTenantsGrid.selectedItem }
                            ) { tenantToRename, newTenantName ->
                                refreshGridRows(
                                    tenantStorage.renameTenant(
                                        log,
                                        tenantToRename,
                                        newTenantName
                                    )
                                )
                            }

                            deleteTenantMenuItem = deleteTenantMenuItem(
                                appLayoutLocale,
                                selectedRow = { editTenantsGrid.selectedItem },
                                childrenCount = { tenantToDelete ->
                                    val photoStorage = tenantStorage.photoStorage(tenantToDelete.name)
                                    Tenant.ChildrenCount(
                                        albums = photoStorage.albums().count(),
                                        photos = photoStorage.allPhotos().count(),
                                        personPhotos = photoStorage.allPersonPhotos().count(),
                                    )
                                },
                            ) { tenantToDelete ->
                                tenantStorage.removeTenant(log, tenantToDelete)
                                refreshGridRows()
                            }

                            changeEditAccessCodeMenuItem = changeEditAccessCodeMenuItem(
                                appLayoutLocale,
                                selectedRow = { editTenantsGrid.selectedItem },
                                currentEditAccessCode = { tenantToChangeAccessCode ->
                                    val photoStorage = tenantStorage.photoStorage(tenantToChangeAccessCode.name)
                                    photoStorage.editAccessCodeFromFile()
                                },
                            ) { tenantToChangeAccessCode, newEditAccessCode ->
                                val photoStorage = tenantStorage.photoStorage(tenantToChangeAccessCode.name)
                                photoStorage.editAccessCodeToFile(newEditAccessCode)
                            }


                        }


                    }

                    editTenantsGrid = editTenantsGrid(appLayoutLocale) { isRowSelected ->

                        deleteTenantMenuItem.isEnabled = isRowSelected
                        renameTenantMenuItem.isEnabled = isRowSelected
                        changeEditAccessCodeMenuItem.isEnabled = isRowSelected

                    }
                    refreshGridRows()

                }

                verticalLayout {

                    configureWithFlexGrow(1.0)

                    isVisible = false

                    // albumsEditor = albumEditor(photoStorage, editTenantsGrid)

                }

            }

        }

    }

}



