package vlite.core.ui.exceptionhandlers

import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.ErrorParameter
import com.vaadin.flow.router.InternalServerError

abstract class KInternalServerErrorHandlerA : InternalServerError(),
    KCustomErrorHandlerA.Holder {

    override fun setErrorParameter(event: BeforeEnterEvent, parameter: ErrorParameter<Exception>) =
        customErrorHandler.handleInternalServerError(event, parameter, element)

}