package vlite.digikamweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import vlite.core.andRun
import vlite.core.buildApplication
import vlite.core.from

@EnableWebMvc
//@EnableVaadin("vlite")
@SpringBootApplication
class DigikamWebApplication

fun main(args: Array<String>) {
    buildApplication<DigikamWebApplication> {
        from { appBeans() }
        andRun(args)
    }
}
