package vlite.core.ui

import com.github.mvysny.karibudsl.v10.flexGrow
import com.vaadin.flow.component.orderedlayout.VerticalLayout

/**
 * --- Vaadin Grid in Horizontal Layout is not filling screen and flex grow is not working https://stackoverflow.com/questions/51397235/vaadin-grid-in-horizontal-layout-is-not-filling-screen-and-flex-grow-is-not-work
 * The fix is to add flex-basis: auto to <vaadin-grid>
 *     and set the width of VerticalLayout to null.
 */
fun VerticalLayout.setWidthUndefined() {
    width = null
}

fun VerticalLayout.configureWithFlexGrow(flexGrow: Double) {
    isPadding = false
    setHeightFull()
    setWidthUndefined()
    this.flexGrow = flexGrow
}
