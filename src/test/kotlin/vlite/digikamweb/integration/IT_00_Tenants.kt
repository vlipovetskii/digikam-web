package vlite.digikamweb.integration

import com.github.mvysny.kaributesting.v10.*
import com.github.mvysny.kaributools.IconName
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.menubar.MenuBar
import com.vaadin.flow.component.textfield.TextField
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import vlite.AbstractAppTest
import vlite.core.kTestFactory
import vlite.digikamweb.AppTestBeansInitializer
import vlite.digikamweb.ui.view.admin.AdminView

@SpringBootTest
@ContextConfiguration(initializers = [AppTestBeansInitializer::class])
@Suppress("ClassName")
class IT_00_Tenants(
    @Autowired override val beanFactory: BeanFactory,
    @Autowired override val applicationContext: ApplicationContext,
) : AbstractAppTest() {

    @TestFactory
    fun testFactory() = kTestFactory {

        "Add tenant-1" {

            navigateToAdminView()
            expectView<AdminView>()

            with(_get<AdminView>()) {
                // _expect<MenuBar>()
                // technically _expect is the same as _find, since _expect invokes _find
                // _get also invokes _find, so let's leave _get only
                with(_get<MenuBar>()) {
                    _expect<MenuItem>(4)

                    _click(_getMenuItemWith(VaadinIcon.PLUS.create()))

                    // currentUI._expect<Dialog>()
                    with(currentUI._get<Dialog>()) {

                        val itemTextField = _get<TextField>()
                        itemTextField.value = "1111"

                        _expect<Button>(2)

                        val saveButton = _get<Button> {
                            icon = IconName.of(VaadinIcon.CHECK)
                        }
                        saveButton._click()

                    }

                }
            }

        }

        "test2" {

        }

    }

}
