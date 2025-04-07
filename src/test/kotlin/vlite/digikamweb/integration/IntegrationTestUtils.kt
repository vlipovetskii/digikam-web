package vlite.digikamweb.integration

import com.github.mvysny.kaributesting.v10._find
import com.github.mvysny.kaributools.IconName
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.menubar.MenuBar
import kotlin.streams.asSequence

@Suppress("TestFunctionName")
fun MenuBar._getMenuItemWith(icon: Icon) =
    _find<MenuItem>().first {
        it.children.asSequence().firstOrNull { it is Icon && IconName.fromComponent(it) == IconName.fromComponent(icon) } != null
    }
