package vlite.core.ui

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.orderedlayout.Scroller
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.dom.Element
import com.vaadin.flow.server.WebBrowser

// TODO Remove after release karibu-dsl 2.4.0
/**
 * Adapter, which provides dummy [HasComponents] receiver
 * for some karibu-dsl methods accepting parameter block: (@VaadinDsl HasComponents).() -> Unit
 */
public interface DummyHasComponents : HasComponents {
    override fun getElement(): Element =
        throw UnsupportedOperationException("Not expected to be called")
}

// TODO Remove after release karibu-dsl 2.4.0
/**
 * Runs DSL function in given [block], then returns the component produced by the DSL function:
 * ```kotlin
 * val vl = provideSingleComponentOrNull {
 *   verticalLayout {
 *     button("Hi!")
 *   }
 * }
 * ```
 */
@VaadinDsl
public fun buildSingleComponentOrNull(block: (@VaadinDsl HasComponents).() -> Any?): Component? {
    var component: Component? = null
    object : DummyHasComponents {
        override fun add(vararg components: Component) {
            require(components.size < 2) { "Too many components to add - this component can only host one! ${components.toList()}" }
            check(component == null) { "Too many components to add - this component can only host one!" }
            component = components.firstOrNull()
        }
    }.block()
    return component
}

// TODO Remove after release karibu-dsl 2.4.0
/**
 * Runs DSL function in given [block], then returns the component produced by the DSL function:
 * ```kotlin
 * val vl = provideSingleComponentOrNull {
 *   verticalLayout {
 *     button("Hi!")
 *   }
 * }
 * ```
 */
@VaadinDsl
public fun buildSingleComponent(block: (@VaadinDsl HasComponents).() -> Any?): Component {
    val component: Component? = buildSingleComponentOrNull(block)
    return checkNotNull(component) { "`block` must add exactly one component" }
}

// TODO Remove after release karibu-dsl 2.4.0
@VaadinDsl
public fun <C : Component> (@VaadinDsl Scroller).content(block: (@VaadinDsl HasComponents).() -> C): C {
    element.removeAllChildren()
    content = buildSingleComponent(block)
    return content as C
}

// TODO PR
@VaadinDsl
public fun <TComponent : Component> (@VaadinDsl Upload).button(block: (@VaadinDsl HasComponents).() -> TComponent) {
    element.removeAllChildren()
    uploadButton = buildSingleComponent {
        block()
    }
}

/**
 * PRB: When Chrome developer tools is used to simulate mobile device,
 * [WebBrowser.isAndroid], [WebBrowser.isIPhone], [WebBrowser.isWindowsPhone] still return false.
 * WO: Configure environment variable KVD_TEST_MOBILE_DEVICE=true to simulate mobile device.
 */
@Suppress("unused")
val WebBrowser.isOnMobileDevice
    get() = System.getenv("KVD_TEST_MOBILE_DEVICE") == "true" ||
            isAndroid || isIPhone || isWindowsPhone

