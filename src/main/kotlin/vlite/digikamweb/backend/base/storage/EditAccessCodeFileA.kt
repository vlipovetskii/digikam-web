package vlite.digikamweb.backend.base.storage

import java.nio.file.Path

interface EditAccessCodeFileA {

    val editAccessCodeFilePath: Path

    interface Implementation : EditAccessCodeFileA {

        val editAccessCodeDirectoryPath: Path

        override val editAccessCodeFilePath: Path get() = editAccessCodeDirectoryPath.resolve(".edit-access-code")

    }

}