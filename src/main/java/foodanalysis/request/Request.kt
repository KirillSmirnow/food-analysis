package foodanalysis.request

import foodanalysis.analysis.Report
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*
import java.util.UUID.randomUUID

data class Request(
    val id: UUID,
    val createdOn: LocalDateTime,
    val userId: UUID,
    val image: Image? = null,
    var text: String? = null,
    var report: Report? = null
) {
    companion object {
        fun ofText(userId: UUID, text: String): Request {
            return Request(randomUUID(), now(), userId, text = text)
        }

        fun ofImage(userId: UUID, imageId: String): Request {
            val image = Image(systemId = imageId)
            return Request(randomUUID(), now(), userId, image = image)
        }

        fun ofTelegramImage(userId: UUID, imageId: String): Request {
            val image = Image(telegramId = imageId)
            return Request(randomUUID(), now(), userId, image = image)
        }
    }
}
