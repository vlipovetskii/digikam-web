package vlite.core

import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans

inline fun <reified TSpringApplication : Any> buildApplication(block: SpringApplicationBuilder.() -> Unit) {
    SpringApplicationBuilder(TSpringApplication::class.java).apply(block)
}

fun SpringApplicationBuilder.fromBeans(init: BeanDefinitionDsl.() -> Unit) {
    initializers(beans { init() })
}

fun SpringApplicationBuilder.andRun(args: Array<String>) {
    run(*args)
}
