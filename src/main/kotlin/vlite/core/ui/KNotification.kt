@file:Suppress("unused")

package vlite.core.ui

import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant

object KNotification {

    const val DEFAULT_DURATION = 3000
    val DEFAULT_POSITION = Notification.Position.TOP_CENTER

    fun error(text: String, duration: Int = DEFAULT_DURATION, position: Notification.Position = DEFAULT_POSITION) {
        Notification.show(text, duration, position).addThemeVariants(NotificationVariant.LUMO_ERROR)
    }

    fun warning(text: String, duration: Int = DEFAULT_DURATION, position: Notification.Position = DEFAULT_POSITION) {
        Notification.show(text, duration, position).addThemeVariants(NotificationVariant.LUMO_PRIMARY)
    }

    fun info(text: String, duration: Int = DEFAULT_DURATION, position: Notification.Position = DEFAULT_POSITION) {
        Notification.show(text, duration, position).addThemeVariants(NotificationVariant.LUMO_SUCCESS)
    }

}

