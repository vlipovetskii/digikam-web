package vlite.digikamweb.ui.base.view

import com.vaadin.flow.router.BeforeEnterEvent
import org.slf4j.Logger
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.objects.TenantName
import vlite.digikamweb.ui.base.view.access.AccessErrorView
import kotlin.jvm.optionals.getOrNull
import kotlin.properties.Delegates

sealed class ViewRouteParameter<TRouteParameterValue : Any>(
    val routeParameterName: String
) {

    /**
     * [Route Templates] https://vaadin.com/docs/latest/flow/routing/additional-guides/route-templates
     * ```
     * Route template parameters must use the following syntax:
     *      :parameter_name[modifier][(regex)]
     *      where:
     *          parameter_name is the name of the parameter
     *          whose value to retrieve when a URL matching the template is resolved on the server.
     *          It must be prefixed by a colon (:).
     *          modifier is optional and may be one of the following:
     *              ? denotes an optional parameter which might be missing from the URL being resolved;
     *              * denotes a wildcard parameter which can be used only as the last segment in the template,
     *              resolving all segment values at the end of the URL.
     *              regex is also optional and defines the regular expression used to match the parameter value.
     *              The regular expression is compiled using java.util.regex.Pattern and
     *              shouldn’t contain the segment delimiter sign /. If the regular expression is missing, the parameter accepts any value.
     * ```
     * [Route Parameters Matching a Regular Expression](https://vaadin.com/docs/latest/flow/routing/additional-guides/route-templates#route-parameters-matching-a-regular-expression)
     * ```
     * @Route("user/:userID?([0-9]{1,9})/edit")
     * ```
     */

    protected var routeParameterValue by Delegates.notNull<TRouteParameterValue>()
    operator fun invoke() =  routeParameterValue
    protected abstract fun String.toRouteParameterValue(): TRouteParameterValue

    fun initFrom(event: BeforeEnterEvent, log: Logger) {
        /**
         * [Route Parameters Matching a Regular Expression](https://vaadin.com/docs/latest/flow/routing/additional-guides/route-templates)
         * ```
         * @Route("user/:userID?([0-9]{1,9})/edit")
         * ```
         */
        event.routeParameters[routeParameterName].getOrNull()?.also { it ->
            routeParameterValue = it.toRouteParameterValue()
        } ?: also {
            log.error("`$routeParameterName` not found in `${event.location}`")
            event.forwardTo(AccessErrorView::class.java)
        }
    }

    class TenantNameValue() : ViewRouteParameter<TenantName>(TenantName.ATTRIBUTE_NAME) {

        companion object {
            const val TEMPLATE = ":${TenantName.ATTRIBUTE_NAME}"
        }

        override fun String.toRouteParameterValue() = TenantName(this)

        interface Holder {
            val tenantNameRouteParameterValue : TenantNameValue
        }

    }

    class EditAccessCodeValue() : ViewRouteParameter<EditAccessCode>(EditAccessCode.ATTRIBUTE_NAME) {

        companion object {
            const val TEMPLATE = ":${EditAccessCode.ATTRIBUTE_NAME}"
        }

        override fun String.toRouteParameterValue() = EditAccessCode(this)

        interface Holder {
            val editAccessCodeRouteParameterValue : EditAccessCodeValue
        }

    }

}