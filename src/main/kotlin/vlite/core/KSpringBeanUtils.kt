package vlite.core

import org.springframework.context.support.BeanDefinitionDsl

inline fun <reified T : Any> BeanDefinitionDsl.beanPrototype(crossinline function: BeanDefinitionDsl.BeanSupplierContext.() -> T) =
    bean(scope = BeanDefinitionDsl.Scope.PROTOTYPE, function = function)
