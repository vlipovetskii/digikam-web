package vlite.core.ui

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.confirmdialog.ConfirmDialog

// TODO PR
/**
 * Example of usage:
 * ```kotlin
 * confirmButton("Delete", VaadinIcon.CHECK.create()) {
 *   css {
 *      backgroundColor = Color.red
 *   }
 *
 *   onClick {
 *     ...
 *   }
 *
 * }
 * ```
 */
@VaadinDsl
public fun ConfirmDialog.confirmButton(
    text: String? = null,
    icon: Component? = null,
    id: String? = null,
    block: (@VaadinDsl Button).() -> Unit
) {
    setConfirmButton(Button(text, icon).apply {
        if (text != null) this.text = text
        if (icon != null) this.icon = icon
        if (id != null) setId(id)
        block()
    })
}

// TODO PR
/**
 * Example of usage:
 * ```kotlin
 * cancelButton("Cancel", VaadinIcon.ARROW_BACKWARD.create()) {
 *
 *   onClick {
 *     this@openConfirmDialog.close()
 *   }
 *
 * }
 * ```
 */
@VaadinDsl
public fun ConfirmDialog.cancelButton(
    text: String? = null,
    icon: Component? = null,
    id: String? = null,
    block: (@VaadinDsl Button).() -> Unit
) {
    setCancelButton(Button(text, icon).apply {
        if (text != null) this.text = text
        if (icon != null) this.icon = icon
        if (id != null) setId(id)
        block()
    })
}
