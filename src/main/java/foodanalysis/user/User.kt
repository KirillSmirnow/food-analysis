package foodanalysis.user

import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*
import java.util.UUID.randomUUID

data class User(
        val id: UUID,
        val createdOn: LocalDateTime,
        val telegramAccount: TelegramAccount?
) {
    companion object {
        fun ofTelegramAccount(telegramAccount: TelegramAccount): User {
            return User(randomUUID(), now(), telegramAccount)
        }
    }
}
