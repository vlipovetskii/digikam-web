package vlite.digikamweb.ui.base.view

import com.github.mvysny.karibudsl.v10.KComposite
import com.vaadin.flow.component.orderedlayout.VerticalLayout

abstract class SideComponentA : KComposite()

fun <TComponent : SideComponentA> TComponent.hideOrShow(isRowSelected: Boolean, onVisibleBlock: TComponent.() -> Unit) {
    val componentParent = parent.get() as VerticalLayout
    componentParent.isVisible = isRowSelected
    if (componentParent.isVisible) {
        onVisibleBlock()
    }

}