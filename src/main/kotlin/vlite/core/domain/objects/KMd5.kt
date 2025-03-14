package vlite.core.domain.objects

import java.security.MessageDigest


@Suppress("unused")
@JvmInline
value class KMd5(val value : ByteArray) {

    companion object {
        fun compute(source : ByteArray) = KMd5(MessageDigest.getInstance("MD5").digest(source))
    }

    interface Holder {
        val md5 : KMd5
    }

}