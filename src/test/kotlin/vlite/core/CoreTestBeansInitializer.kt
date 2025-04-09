package vlite.core

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext

class CoreTestBeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) {
        context.initializeWith { }
    }
}
