package vlite.digikamweb.ui.base.exceptionhandlers

import com.vaadin.flow.router.BeforeEnterEvent
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import vlite.core.ui.exceptionhandlers.KCustomErrorHandlerBaseA
import vlite.digikamweb.ui.base.view.access.AccessErrorView

/**
 * [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
 * [2.2. Global Exception Handling](https://www.baeldung.com/exception-handling-for-rest-with-spring#2-global-exception-handling)
 */
@Suppress("unused")
@ControllerAdvice
class CustomErrorHandler : KCustomErrorHandlerBaseA() {

    override fun forwardToAccessErrorView(event: BeforeEnterEvent) {
        AccessErrorView.forwardToMe(event)
    }

    override fun forwardToAccessErrorView(response: HttpServletResponse) {
        AccessErrorView.forwardToMe(response)
    }

    /**
     * [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
     * [2. @ExceptionHandler](https://www.baeldung.com/exception-handling-for-rest-with-spring#exceptionhandler)
     * [Performing a redirect from a spring MVC @ExceptionHandler method](https://stackoverflow.com/questions/14961869/performing-a-redirect-from-a-spring-mvc-exceptionhandler-method)
     */
    @Suppress("unused")
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    override fun handleErrorAtSpringLevel(e: HttpRequestMethodNotSupportedException, response: HttpServletResponse) {
        super.handleErrorAtSpringLevel(e, response)
    }

}