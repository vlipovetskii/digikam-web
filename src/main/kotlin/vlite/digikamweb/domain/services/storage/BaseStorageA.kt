package vlite.digikamweb.domain.services.storage

import org.slf4j.Logger
import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import vlite.digikamweb.domain.objects.EditAccessCode

interface BaseStorageA {

    val exists: Boolean

    fun logErrorIfNotExists(log: Logger)

    fun editAccessCodeFromFile(log: Logger? = null): EditAccessCode?
    fun editAccessCodeToFile(editAccessCode: EditAccessCode)

    interface Implementation<TFileStorage : BaseFileStorageA> : BaseStorageA {

        val fileStorage: TFileStorage

        override val exists get() = fileStorage.directoryExists

        override fun editAccessCodeFromFile(log: Logger?) =
            fileStorage.editAccessCodeFromFile(log)?.let {
                EditAccessCode(it).also {
                    require(it.isValid) { "`${it.value}` is not valid" }
                }
            }

        override fun editAccessCodeToFile(editAccessCode: EditAccessCode) {
            fileStorage.editAccessCodeToFile(editAccessCode.value)
        }

    }

}