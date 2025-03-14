package vlite.digikamweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import vlite.core.andRun
import vlite.core.buildApplication
import vlite.core.fromBeans
import vlite.digikamweb.backend.backendBeans
import vlite.digikamweb.domain.services.domainServicesBeans
import vlite.digikamweb.ui.*

@EnableWebMvc
//@EnableVaadin("vlite")
@SpringBootApplication
class DigikamWebApplication

fun main(args: Array<String>) {
    buildApplication<DigikamWebApplication> {
        fromBeans {

            uiBaseBeans()

            backendBeans()

            domainServicesBeans()

            run {
                uiAlbumBeans()
                uiPeopleBeans()
            }

            run {
                uiEditPhotoBeans()
                uiEditPersonPhotoBeans()
                uiEditAlbumsBeans()
                uiEditTenantsBeans()
            }

        }
        andRun(args)
    }
}
