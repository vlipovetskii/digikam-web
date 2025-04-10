package vlite.core

import org.springframework.beans.factory.BeanFactory
import org.springframework.context.ApplicationContext

interface KSpringBootTestA {
    val beanFactory: BeanFactory
    val applicationContext : ApplicationContext
}