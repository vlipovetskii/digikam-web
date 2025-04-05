package vlite.core.ui

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.Grid

// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * Returns the currently selected item. Returns null if nothing is selected.
 * If the Grid is multi-select, returns arbitrary selected item.
 */
public val <T> Grid<T>.selectedItemOrNull: T? get() = selectionModel.firstSelectedItem.orElse(null)
// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * Returns the currently selected item. Fails if nothing is selected.
 * If the Grid is multi-select, returns arbitrary selected item.
 */
public val <T> Grid<T>.selectedItem: T get() = selectionModel.firstSelectedItem.get()
// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * Shorthand for [com.vaadin.flow.data.provider.DataProvider.refreshItem].
 */
public fun <T> Grid<T>.refreshItem(item: T) = dataProvider.refreshItem(item)

// TODO Remove after release karibu-dsl 2.4.0
@VaadinDsl
public fun <T, C : Component> (@VaadinDsl Grid<T>).componentColumn(
    componentProvider: HasComponents.(T) -> C?,
    block: (@VaadinDsl Grid.Column<T>).() -> Unit = {}
): Grid.Column<T> {
    val column = addComponentColumn {
        buildSingleComponentOrNull {
            componentProvider(it)
        }
    }
    column.block()
    return column
}
