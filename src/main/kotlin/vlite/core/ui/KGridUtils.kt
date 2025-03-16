package vlite.core.ui

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.Grid

// TODO PR
/**
 * [Grid] Shorthands for convenience
 */
public val <T> Grid<T>.selectedRowOrNull get() = selectedItems.firstOrNull()
public val <T> Grid<T>.selectedRow: T get() = selectedItems.first()
public fun <T> Grid<T>.refreshRow(row: T) = dataProvider.refreshItem(row)

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
