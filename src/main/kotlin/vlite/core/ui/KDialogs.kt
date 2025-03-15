package vlite.core.ui

import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.karibuDslI18n
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
public fun ConfirmDialog.setConfirm(
    text: String? = null,
    icon: Component? = null,
    id: String? = null,
    block: (@VaadinDsl Button).() -> Unit = {}
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
 * Adds a cancel button to the dialog; the dialog is closed when the button is clicked.
 * @param buttonText the caption of the button.
 *
 * Example of usage:
 * ```kotlin
 * cancelButton("Cancel", VaadinIcon.ARROW_BACKWARD.create()) {
 *
 *   onClick {
 *     this@openConfirmDialog.close()
 *   }
 *
 * }
 *
 * ```
 */
@VaadinDsl
public fun ConfirmDialog.setCloseOnCancel(
    buttonText: String? = karibuDslI18n("cancel"),
    icon: Component? = null,
    id: String? = null,
    block: (@VaadinDsl Button).() -> Unit = {}
) {
/*
    setCancelButton(Button(buttonText, icon).apply {
        if (buttonText != null) this.text = buttonText
        if (icon != null) this.icon = icon
        if (id != null) setId(id)
        block()
    })
*/
    /**
     * PRB: setCancelButton(component: Component) is not displayed
     * Temporary: setCancelButton("Cancel") { close() }
     * Solution: TODO issue to Vaadin github repo
     */
    setCancelButton(buttonText) { close() }
}
