package vlite.digikamweb

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import vlite.core.initializeWith

class AppTestBeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) {
        context.initializeWith { appBeans() }
    }
}
