package vlite.core.domain.services

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * [LocalDate]: toLocalDateTime().toLocalDate
 * [LocalTime]: toLocalDateTime().toLocalTime
 */
fun Date.toLocalDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime =
    Instant.ofEpochMilli(time).atZone(zoneId).toLocalDateTime()

fun currentLocalDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime = LocalDateTime.now(zoneId)

/**
 * "yyyy-MM-dd HH:mm:ss"
 */
fun LocalDateTime.toStringWithFormat(pattern: String = "yyyy-MM-dd HH:mm:ss", locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofPattern(pattern).withLocale(locale))
