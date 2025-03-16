package vlite.core.ui

import com.github.mvysny.karibudsl.v10.onClick
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.confirmdialog.ConfirmDialog

// TODO PR
/**
 * Sets the confirm button as dangerous, e.g. when you're confirming a deletion action
 * that can not be reversed.
 */
public fun Button.setConfirmIsDanger() {
    addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY)
}


// TODO PR
/**
 * Adds a cancel button to the dialog; the dialog is closed when the button is clicked.
 * @param button custom button component.
 */
public fun ConfirmDialog.setCloseOnCancelButton(button: Button) {
    setCancelButton(button)
    setCancelable(true)
    button.onClick { close() }
}
