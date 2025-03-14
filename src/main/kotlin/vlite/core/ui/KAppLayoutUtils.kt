package vlite.core.ui

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.shared.SlotUtils
import com.vaadin.flow.server.VaadinSession

// TODO Remove after release karibu-dsl 2.4.0
/**
 * [removeNavbar] removes all components from the navbar slot.
 * Also see [AppLayout.addToNavbar]
 */
@VaadinDsl
public fun (@VaadinDsl AppLayout).removeNavbar(touchOptimized: Boolean) {
    val slot = "navbar" + (if (touchOptimized) " touch-optimized" else "")
    SlotUtils.clearSlot(this, slot)
}

// TODO Remove after release karibu-dsl 2.4.0
/**
 * Allows you to populate the [AppLayout.addToNavbar] in a DSL fashion:
 * ```
 * appLayout {
 *   navbar {
 *     drawerToggle()
 *     h3("My App")
 *   }
 * }
 * ```
 */
@VaadinDsl
public fun (@VaadinDsl AppLayout).navbar(touchOptimized: Boolean = false, block: (@VaadinDsl HasComponents).() -> Unit = {}) {
    object : DummyHasComponents {
        override fun add(vararg components: Component) {
            addToNavbar(touchOptimized, *components)
        }
    }.block()
}

// TODO Remove after release karibu-dsl 2.4.0
/**
 * [removeDrawer] removes all components from the drawer slot.
 * Also see [AppLayout.addToDrawer]
 */
@VaadinDsl
public fun (@VaadinDsl AppLayout).removeDrawer() {
    SlotUtils.clearSlot(this, "drawer")
}

// TODO Remove after release karibu-dsl 2.4.0
/**
 * Populates the AppLayout drawer slot. `sideNav{}` is the best way to populate the drawer with Vaadin 24.
 */
@VaadinDsl
public fun (@VaadinDsl AppLayout).drawer(block: (@VaadinDsl HasComponents).() -> Unit = {}) {
    object : DummyHasComponents {
        override fun add(vararg components: Component) {
            addToDrawer(*components)
        }
    }.block()
}

// TODO Remove after release karibu-dsl 2.4.0
/**
 * [removeContent] removes all components from the content.
 * Also see [AppLayout.setContent]
 */
@VaadinDsl
public fun (@VaadinDsl AppLayout).removeContent() {
    content = null
}

// TODO Remove after release karibu-dsl 2.4.0
/**
 * Allows you to set the [AppLayout.setContent] in a DSL fashion.
 *
 * The [AppLayout] implements [RouterLayout] and is able to automatically populate itself with the view's contents.
 * If you wish to take advantage of this feature, just make your main layout extend the [AppLayout] class.
 */
@VaadinDsl
public fun (@VaadinDsl AppLayout).content(block: (@VaadinDsl HasComponents).() -> Unit = {}) {
    object : DummyHasComponents {
        override fun add(vararg components: Component) {
            require(components.size < 2) { "Too many components to add - AppLayout content can only host one! ${components.toList()}" }
            val component = components.firstOrNull() ?: return
            check(this@content.content == null) { "The content has already been initialized!" }
            this@content.content = component
        }
    }.block()
}

fun AppLayout.closeDrawerOnMobileDevice() {
    if (VaadinSession.getCurrent().browser.isOnMobileDevice) isDrawerOpened = false
}

// val AppLayout.thisAppLayout : AppLayout get() = this
