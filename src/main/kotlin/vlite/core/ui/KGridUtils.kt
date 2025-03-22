package vlite.core.ui

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.Grid

// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * [Grid] Shorthands for convenience
 */
public val <T> Grid<T>.selectedItemOrNull: T? get() = selectionModel.firstSelectedItem.orElseGet(null)
public val <T> Grid<T>.selectedItem: T get() = selectionModel.firstSelectedItem.get()
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
