package vlite.digikamweb.ui.base.exceptionhandlers

import com.vaadin.flow.component.Tag
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.ErrorParameter
import com.vaadin.flow.router.NotFoundException
import com.vaadin.flow.router.RouteNotFoundError


@Suppress("unused")
@Tag(Tag.DIV)
open class KRouteNotFoundErrorHandler(override val customErrorHandler: KCustomErrorHandlerA) : RouteNotFoundError(),
    KCustomErrorHandlerA.Holder {

    override fun setErrorParameter(event: BeforeEnterEvent, parameter: ErrorParameter<NotFoundException>) =
        customErrorHandler.handleRouteNotFoundError(event, parameter, element)

}