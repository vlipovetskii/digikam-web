package vlite.core

import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

fun GenericApplicationContext.initializeWith(init: BeanDefinitionDsl.() -> Unit) {
    beans { init() }.initialize(this)
}

