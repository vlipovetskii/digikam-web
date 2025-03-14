package vlite.digikamweb.ui.base.exceptionhandlers

import com.vaadin.flow.dom.Element
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.ErrorParameter
import com.vaadin.flow.router.NotFoundException
import com.vaadin.flow.server.ErrorEvent

interface KCustomErrorHandlerA {

    fun handleErrorAtSessionLevel(event: ErrorEvent)
    fun handleInternalServerError(event: BeforeEnterEvent, parameter: ErrorParameter<Exception>, element: Element): Int
    fun handleRouteNotFoundError(event: BeforeEnterEvent, parameter: ErrorParameter<NotFoundException>, element: Element): Int

    interface Holder {
        val customErrorHandler : KCustomErrorHandlerA
    }

}