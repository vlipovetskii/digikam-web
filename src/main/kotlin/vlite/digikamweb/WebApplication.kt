package vlite.digikamweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import vlite.core.andRun
import vlite.core.buildApplication
import vlite.core.from

@EnableWebMvc
//@EnableVaadin("vlite")
@SpringBootApplication
class WebApplication

fun main(args: Array<String>) {
    buildApplication<WebApplication> {
        from { appBeans() }
        andRun(args)
    }
}
