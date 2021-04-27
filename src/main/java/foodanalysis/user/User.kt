package foodanalysis.user

import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*
import java.util.UUID.randomUUID

data class User(
    val id: UUID,
    val createdOn: LocalDateTime,
    val clientId: String? = null,
    val telegramAccount: TelegramAccount? = null
) {
    companion object {
        fun ofClientId(clientId: String): User {
            return User(randomUUID(), now(), clientId = clientId)
        }

        fun ofTelegramAccount(telegramAccount: TelegramAccount): User {
            return User(randomUUID(), now(), telegramAccount = telegramAccount)
        }
    }
}
