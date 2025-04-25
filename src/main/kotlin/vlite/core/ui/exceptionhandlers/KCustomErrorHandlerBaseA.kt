package vlite.core.ui.exceptionhandlers

import com.vaadin.flow.component.html.Span
import com.vaadin.flow.dom.Element
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.ErrorParameter
import com.vaadin.flow.router.NotFoundException
import com.vaadin.flow.server.ErrorEvent
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.HttpRequestMethodNotSupportedException
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.core.ui.KNotification

abstract class KCustomErrorHandlerBaseA : KCustomErrorHandlerA {

    companion object : KLoggerA {
        private val log by lazy { classLogger }
    }

    protected open val defaultUserFriendlyMessage: String = "Oops: An error occurred, and we are really sorry about that. Already working on the fix!"

    protected open fun logUncaughtException(source: String, t: Throwable) {
        log.error("$source: Vaadin UI uncaught exception $t", t)
    }

    /**
     * [Custom Error Handling](https://vaadin.com/docs/latest/flow/advanced/custom-error-handler)
     * ```
     * Next, assign the custom error handler to the current user session like this:
     * VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
     * ```
     * SEE [Session & UI Listeners] https://vaadin.com/docs/latest/flow/advanced/session-and-ui-init-listener
     */
    override fun handleErrorAtSessionLevel(event: ErrorEvent) {
        logUncaughtException("ErrorAtSessionLevel", event.throwable)

        // when the exception occurs, show a user-friendly notification to a user
        /**
         * PRB: In some cases Vaadin can't show notification
         * WO: ?
         * Solution: ?
         */
        KNotification.error(defaultUserFriendlyMessage)
    }

    override fun handleInternalServerError(event: BeforeEnterEvent, parameter: ErrorParameter<Exception>, element: Element): Int {
        logUncaughtException("InternalServerError", parameter.exception)
        element.appendChild(Span(defaultUserFriendlyMessage).element)
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    }

    abstract fun forwardToAccessErrorView(event: BeforeEnterEvent)

    /**
     * PRB: KRouteNotFoundErrorHandler doesn't intercept wrong routes, which contain route parameters
     * WO: [handleErrorAtSpringLevel]
     */
    override fun handleRouteNotFoundError(event: BeforeEnterEvent, parameter: ErrorParameter<NotFoundException>, element: Element): Int {
        log.error("Route: `${event.location.path}` is not supported")
        forwardToAccessErrorView(event)
        return HttpServletResponse.SC_NOT_FOUND
    }

    abstract fun forwardToAccessErrorView(response: HttpServletResponse)

    open fun handleErrorAtSpringLevel(e: HttpRequestMethodNotSupportedException, response: HttpServletResponse) {
        logUncaughtException("ErrorAtSpringLevel", e)
        forwardToAccessErrorView(response)
    }

}