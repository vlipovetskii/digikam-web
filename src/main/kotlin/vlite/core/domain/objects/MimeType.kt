package vlite.core.domain.objects

// TODO Remove after release karibu-dsl 2.4.0 (karibu-tools 0.25)
/**
 * NOTE: data class instead of enum class to support producing [MimeType] from arbitrary [contentSubType]
 */
public data class MimeType(val mimeContentType: ContentType, val contentSubType: String = "*") {

    @Suppress("unused")
    public enum class ContentType(public val value: String) {
        APPLICATION("application"),
        AUDIO("audio"),
        EXAMPLE("example"),
        FONT("font"),
        IMAGE("image"),
        MESSAGE("message"),
        MODEL("model"),
        MULTIPART("multipart"),
        TEXT("text"),
        VIDEO("video"),
        CHEMICAL("chemical"),
        FLV_APPLICATION("flv-application"),
        INODE("inode"),
        WWW("www"),
        X_CONFERENCE("x-conference"),
        X_CONTENT("x-content"),
        X_DIRECTORY("x-directory"),
        X_EPOC("x-epoc"),
        X_WORLD("x-world"),
        ZZ_APPLICATION("zz-application"),
        STAR("*");
    }

    @Suppress("unused")
    public companion object {

        public val TEXT_PLAIN: MimeType = MimeType(MimeType.ContentType.TEXT, "plain")
        public val TEXT_HTML: MimeType = MimeType(MimeType.ContentType.TEXT, "html")
        public val XLS: MimeType = MimeType(MimeType.ContentType.APPLICATION, "vnd.ms-excel")
        public val XLSX: MimeType = MimeType(MimeType.ContentType.APPLICATION, "vnd.openxmlformats-officedocument.spreadsheetml.sheet")

        public val IMAGE: MimeType = MimeType(MimeType.ContentType.IMAGE)
        public val IMAGE_JPEG: MimeType = MimeType(MimeType.ContentType.IMAGE, "jpeg")

        public val AUDIO: MimeType = MimeType(MimeType.ContentType.AUDIO)
        public val AUDIO_MP3: MimeType = MimeType(MimeType.ContentType.AUDIO, "mpeg")

        public val VIDEO: MimeType = MimeType(MimeType.ContentType.VIDEO)
        public val VIDEO_AVI: MimeType = MimeType(MimeType.ContentType.VIDEO, "x-msvideo")
        public val VIDEO_MPEG: MimeType = MimeType(MimeType.ContentType.VIDEO, "mpeg")
        public val VIDEO_MP4: MimeType = MimeType(MimeType.ContentType.VIDEO, "mp4")

    }

    override fun toString(): String = "$mimeContentType/$contentSubType"

}