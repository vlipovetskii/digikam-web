package vlite.digikamweb.ui.base.view

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode
import com.vaadin.flow.component.grid.GridVariant
import vlite.core.ui.selectedItemOrNull

typealias OnRowSelected<TGridRow> = Grid<TGridRow>.(isRowSelected: Boolean) -> Unit

fun <TGridRow> Grid<TGridRow>.addAppThemeVariants() {
    addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_ROW_BORDERS)
}

fun <TGridRow> Grid<TGridRow>.addAppSelectionListener(onRowSelected: OnRowSelected<TGridRow>) {
    selectionMode = SelectionMode.SINGLE
    addSelectionListener {
        onRowSelected(selectedItemOrNull != null)
    }
}

fun <TGridRow> Grid<TGridRow>.defaultConfiguration(onRowSelected: OnRowSelected<TGridRow>) {
    setHeightFull()

    addAppThemeVariants()

    addAppSelectionListener(onRowSelected)
}
