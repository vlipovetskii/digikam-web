package vlite.digikamweb.ui.base.view.access

import com.github.mvysny.karibudsl.v10.css
import com.github.mvysny.karibudsl.v10.icon
import com.github.mvysny.karibudsl.v10.span
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.Route
import jakarta.servlet.http.HttpServletResponse
import kotlinx.css.Color
import kotlinx.css.color
import vlite.core.ui.i18n.KLocaleChangeObserverA
import vlite.digikamweb.ui.base.i18n.AppI18NProvider
import vlite.digikamweb.ui.base.view.access.AccessErrorView.Companion.ROUTE
import java.util.*

@Route(ROUTE)
class AccessErrorView : VerticalLayout(), KLocaleChangeObserverA {

    companion object {
        private const val ROUTE = "no-access"

        fun forwardToMe(event: BeforeEnterEvent) {
            event.forwardTo(AccessErrorView::class.java)
        }

        fun forwardToMe(response: HttpServletResponse) {
            response.sendRedirect("/$ROUTE")
        }

    }

    private enum class I18n(
        override val en: String,
        override val ru: String,
        override val he: String
    ) : AppI18NProvider.ProvidedValuesA {

        ErrorMessage(
            en = "Access error",
            ru = "Ошибка доступа",
            he = "שגיאת גישה"
        ),

    }

    override fun localeChange(locale: Locale) {
        errorText.text = I18n.ErrorMessage.translation(locale)
    }

    private var errorText: Span

    init {
        icon(VaadinIcon.WARNING) {
            css {
                color = Color.red
            }
        }
        errorText = span()
    }

}