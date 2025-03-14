package vlite.core.ui

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.ComponentUtil
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs

// TODO Remove after release karibu-dsl 2.4.0
/**
 * Retrieves data stored via [ComponentUtil.setData]/[ComponentUtil.getData], creating it if it doesn't exist.
 */
internal fun <T> Component.data(key: String, whenMissing: () -> T): T {
    @Suppress("UNCHECKED_CAST") var value: T? = ComponentUtil.getData(this, key) as T?
    if (value == null) {
        value = whenMissing()
        ComponentUtil.setData(this, key, value)
    }
    return value!!
}


// TODO Remove after release karibu-dsl 2.4.0
public typealias OnTabSelectedHandler = () -> Unit

// TODO Remove after release karibu-dsl 2.4.0
/**
 * Store/Retrieve the state into/from [Tabs] via [ComponentUtil.setData]/[ComponentUtil.getData]
 */
private val (@VaadinDsl Tabs).onSelectedHandlers: MutableMap<Tab, OnTabSelectedHandler>
    get() = data("onSelectedHandlers") {
        val handlers = mutableMapOf<Tab, OnTabSelectedHandler>()
        addSelectedChangeListener { handlers[it.selectedTab]?.invoke() }
        handlers
    }

// TODO Remove after release karibu-dsl 2.4.0
@VaadinDsl
public fun (@VaadinDsl Tab).onSelected(handler: OnTabSelectedHandler) : OnTabSelectedHandler {
    val tabs = parent.get() as Tabs
    tabs.onSelectedHandlers[this] = handler
    if (tabs.selectedTab == this) {
        handler()
    }
    return handler
}
