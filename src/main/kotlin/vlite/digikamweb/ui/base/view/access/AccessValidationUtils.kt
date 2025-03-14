package vlite.digikamweb.ui.base.view.access

import com.github.mvysny.kaributools.navigateTo
import org.slf4j.Logger
import vlite.digikamweb.domain.objects.EditAccessCode
import vlite.digikamweb.domain.services.storage.BaseStorageA

fun BaseStorageA.validate(
    log: Logger,
    onSuccess: () -> Unit
) {
    if (exists) {
        onSuccess()
    } else {
        logErrorIfNotExists(log)
        navigateTo<AccessErrorView>()
    }
}

fun EditAccessCode.validate(
    log: Logger,
    editAccessCodeFromFile: EditAccessCode,
    onSuccess: () -> Unit
)  {

    if (this == editAccessCodeFromFile) {
        onSuccess()
    } else {
        log.error("editAccessCode: `${this.value}` != editAccessCodeFromFile: `${editAccessCodeFromFile.value}`")
        navigateTo<AccessErrorView>()
    }
}

