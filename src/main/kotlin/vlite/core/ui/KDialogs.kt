package vlite.core.ui

import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onClick
import com.vaadin.flow.component.ClickNotifier
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.confirmdialog.ConfirmDialog
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon

// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * Sets the button as dangerous, e.g. when you're confirming a deletion action
 * that can not be reversed.
 *
 * Example of usage:
 * ```kotlin
 * setConfirmButton(Button("Delete", VaadinIcon.Trash.create()).apply {
 *          setDanger()
 *          onClick {...}
 *      }
 * )
 * ```
 */
public fun Button.setDanger() {
    addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY)
}

// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * Adds a cancel clickable component (e.g. Button) to the dialog; the dialog is closed when the button is clicked.
 * @param clickableComponent custom clickable component.
 *
 * Example of usage:
 * ```kotlin
 * setCloseOnCancel(Button("Cancel", VaadinIcon.ARROW_BACKWARD.create()))
 * ```
 */
public fun <T> ConfirmDialog.setCloseOnCancel(clickableComponent: T)
        where T : Component, T : ClickNotifier<T> {
    setCancelButton(clickableComponent)
    setCancelable(true)
    clickableComponent.onClick { close() }
}

fun ConfirmDialog.standardConfirmButton(text: String? = null, icon: Icon? = VaadinIcon.CHECK.create(), onConfirmButtonClick: () -> Unit) =
    buildSingleComponent {
        button(
            text,
            icon
        ) {
            setDanger()
            onClick {
                onConfirmButtonClick()
            }
        }
    } as Button

fun ConfirmDialog.standardCloseOnCancelButton(text: String? = null, icon: Icon? = VaadinIcon.ARROW_BACKWARD.create()) =
    buildSingleComponent {
        button(
            text,
            icon
        )
    } as Button
