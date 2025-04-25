package vlite.digikamweb.ui.exceptionhandlers

import com.vaadin.flow.component.Tag
import vlite.core.ui.exceptionhandlers.KCustomErrorHandlerA
import vlite.core.ui.exceptionhandlers.KInternalServerErrorHandlerA

@Suppress("unused")
@Tag(Tag.DIV)
open class InternalServerErrorHandler(
    override val customErrorHandler: KCustomErrorHandlerA
) : KInternalServerErrorHandlerA()