package vlite.digikamweb.integration

import com.github.mvysny.kaributesting.v10.*
import com.github.mvysny.kaributools.IconName
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.menubar.MenuBar
import com.vaadin.flow.component.textfield.TextField
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.AbstractAppTest
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.digikamweb.AppTestBeansInitializer
import vlite.digikamweb.domain.objects.TenantName
import vlite.digikamweb.domain.services.storage.TenantStorageA
import vlite.digikamweb.ui.view.admin.AdminView
import vlite.digikamweb.ui.view.admin.edittenants.EditTenantsGridRow
import kotlin.streams.asSequence
import kotlin.test.expect

@SpringBootTest
@ContextConfiguration(initializers = [AppTestBeansInitializer::class])
@Suppress("ClassName")
class IT_00_Tenants(
    @Autowired override val beanFactory: BeanFactory,
    @Autowired override val applicationContext: ApplicationContext,
) : AbstractAppTest() {

    companion object : KLoggerA {
        private val log by lazy { classLogger }
    }

    @TestFactory
    fun testFactory() = testFactory {

        testGroup("Edit tenants") {

            "Add tenant-1" {

                navigateToAdminView()
                expectView<AdminView>()

                with(_get<AdminView>()) {
                    // _expect<MenuBar>()
                    // technically _expect is the same as _find, since _expect invokes _find
                    // _get also invokes _find, so let's leave _get only
                    with(_get<MenuBar>()) {
                        _expect<MenuItem>(4)

                        expect(_getMenuItemWith(VaadinIcon.PLUS.create()).isEnabled) { true }
                        expect(_getMenuItemWith(VaadinIcon.EDIT.create()).isEnabled) { false }
                        expect(_getMenuItemWith(VaadinIcon.TRASH.create()).isEnabled) { false }
                        expect(_getMenuItemWith(VaadinIcon.PASSWORD.create()).isEnabled) { false }

                        _click(_getMenuItemWith(VaadinIcon.PLUS.create()))

                        // currentUI._expect<Dialog>()
                        with(currentUI._get<Dialog>()) {

                            val saveButton = _get<Button> {
                                icon = IconName.of(VaadinIcon.CHECK)
                            }

                            expect(saveButton.isEnabled) { false }

                            val itemTextField = _get<TextField>()
                            itemTextField._setValue("1111")

                            _expect<Button>(2)

                            expect(saveButton.isEnabled) { true }
                            saveButton._click()

                        }

                    }
                }

            }

            "Rename tenant-1" {

                val tenantStorage = beanFactory.getBean<TenantStorageA>()
                val newTenant = tenantStorage.addTenant(log, TenantName("1111"))

                navigateToAdminView()
                expectView<AdminView>()

                with(_get<AdminView>()) {
                    // _expect<MenuBar>()
                    // technically _expect is the same as _find, since _expect invokes _find
                    // _get also invokes _find, so let's leave _get only

                    val grid = _get<Grid<EditTenantsGridRow>>()

                    with(_get<MenuBar>()) {
                        _expect<MenuItem>(4)

                        expect(_getMenuItemWith(VaadinIcon.PLUS.create()).isEnabled) { true }
                        expect(_getMenuItemWith(VaadinIcon.EDIT.create()).isEnabled) { false }
                        expect(_getMenuItemWith(VaadinIcon.TRASH.create()).isEnabled) { false }
                        expect(_getMenuItemWith(VaadinIcon.PASSWORD.create()).isEnabled) { false }

                        grid.select(grid.listDataView.items.asSequence().first { r-> r.tenant == newTenant })

                        expect(_getMenuItemWith(VaadinIcon.PLUS.create()).isEnabled) { true }
                        expect(_getMenuItemWith(VaadinIcon.EDIT.create()).isEnabled) { true }
                        expect(_getMenuItemWith(VaadinIcon.TRASH.create()).isEnabled) { true }
                        expect(_getMenuItemWith(VaadinIcon.PASSWORD.create()).isEnabled) { true }

                        _click(_getMenuItemWith(VaadinIcon.EDIT.create()))

                        // currentUI._expect<Dialog>()
                        with(currentUI._get<Dialog>()) {

                            val saveButton = _get<Button> {
                                icon = IconName.of(VaadinIcon.CHECK)
                            }

                            expect(saveButton.isEnabled) { false }

                            val itemTextField = _get<TextField>()
                            itemTextField._setValue("1112")

                            _expect<Button>(2)

                            expect(saveButton.isEnabled) { true }
                            saveButton._click()

                        }

                    }
                }

            }

        }

        "test2" {

        }

    }

//    @TestFactory
//    fun testFactory2() = kTestFactory {
//    }

}
