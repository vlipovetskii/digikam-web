package vlite.digikamweb.ui.base.exceptionhandlers

import com.vaadin.flow.component.Tag
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.ErrorParameter
import com.vaadin.flow.router.InternalServerError

@Suppress("unused")
@Tag(Tag.DIV)
open class KInternalServerErrorHandler(
    override val customErrorHandler: KCustomErrorHandlerA
) : InternalServerError(),
    KCustomErrorHandlerA.Holder {

    override fun setErrorParameter(event: BeforeEnterEvent, parameter: ErrorParameter<Exception>) =
        customErrorHandler.handleInternalServerError(event, parameter, element)

}