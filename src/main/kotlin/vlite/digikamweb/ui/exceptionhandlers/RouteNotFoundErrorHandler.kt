package vlite.digikamweb.ui.exceptionhandlers

import com.vaadin.flow.component.Tag
import vlite.core.ui.exceptionhandlers.KCustomErrorHandlerA
import vlite.core.ui.exceptionhandlers.KRouteNotFoundErrorHandlerA


@Suppress("unused")
@Tag(Tag.DIV)
open class RouteNotFoundErrorHandler(
    override val customErrorHandler: KCustomErrorHandlerA
) : KRouteNotFoundErrorHandlerA()