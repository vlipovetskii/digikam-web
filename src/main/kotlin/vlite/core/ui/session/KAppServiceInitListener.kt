package vlite.core.ui.session

import com.vaadin.flow.server.ServiceInitEvent
import com.vaadin.flow.server.VaadinServiceInitListener
import com.vaadin.flow.server.VaadinSession
import vlite.core.ui.exceptionhandlers.KCustomErrorHandlerA


/**
 * [Session & UI Listeners](https://vaadin.com/docs/latest/flow/advanced/session-and-ui-init-listener)
 */
@Suppress("unused")
open class KAppServiceInitListener(
    override val customErrorHandler: KCustomErrorHandlerA,
) : VaadinServiceInitListener,
    KCustomErrorHandlerA.Holder {

    override fun serviceInit(event: ServiceInitEvent) {
        event.getSource().addSessionInitListener {
            VaadinSession.getCurrent().setErrorHandler { customErrorHandler.handleErrorAtSessionLevel(it) }
        }
    }

}