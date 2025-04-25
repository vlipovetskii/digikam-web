package vlite.core.ui.exceptionhandlers

import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.ErrorParameter
import com.vaadin.flow.router.NotFoundException
import com.vaadin.flow.router.RouteNotFoundError


abstract class KRouteNotFoundErrorHandlerA : RouteNotFoundError(),
    KCustomErrorHandlerA.Holder {

    override fun setErrorParameter(event: BeforeEnterEvent, parameter: ErrorParameter<NotFoundException>) =
        customErrorHandler.handleRouteNotFoundError(event, parameter, element)

}