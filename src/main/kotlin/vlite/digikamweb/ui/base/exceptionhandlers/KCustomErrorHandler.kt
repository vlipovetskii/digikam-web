package vlite.digikamweb.ui.base.exceptionhandlers

import com.vaadin.flow.component.html.Span
import com.vaadin.flow.dom.Element
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.ErrorParameter
import com.vaadin.flow.router.NotFoundException
import com.vaadin.flow.server.ErrorEvent
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import vlite.core.KLoggerA
import vlite.core.classLogger
import vlite.core.ui.KNotification
import vlite.digikamweb.ui.base.view.access.AccessErrorView

@Suppress("unused")
@ControllerAdvice
class KCustomErrorHandler : KCustomErrorHandlerA {

    companion object : KLoggerA {
        private val log by lazy { classLogger }
    }

    private val defaultUserFriendlyMessage: String = "Oops: An error occurred, and we are really sorry about that. Already working on the fix!"

    private fun logUncaughtException(source: String, t: Throwable) {
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

    /**
     * PRB: KRouteNotFoundErrorHandler doesn't intercept wrong routes, which contain route parameters
     * WO: [handleErrorAtSpringLevel]
     */
    override fun handleRouteNotFoundError(event: BeforeEnterEvent, parameter: ErrorParameter<NotFoundException>, element: Element): Int {
        log.error("Route: `${event.location.path}` is not supported")
        AccessErrorView.forwardToMe(event)
        return HttpServletResponse.SC_NOT_FOUND
    }

    /**
     * [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
     * [4. Solution 3: @ControllerAdvice](https://www.baeldung.com/exception-handling-for-rest-with-spring#controlleradvice)
     * [Performing a redirect from a spring MVC @ExceptionHandler method](https://stackoverflow.com/questions/14961869/performing-a-redirect-from-a-spring-mvc-exceptionhandler-method)
     */
    @Suppress("unused")
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleErrorAtSpringLevel(e: HttpRequestMethodNotSupportedException, response: HttpServletResponse) {
        logUncaughtException("ErrorAtSpringLevel", e)
        AccessErrorView.forwardToMe(response)
    }

}