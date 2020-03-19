package foodanalysis.user

import java.util.*

interface UserService {

    fun authenticate(clientId: String)

    fun authenticate(telegramAccount: TelegramAccount)

    fun getAuthenticatedUser(): User

    fun getById(id: UUID): User
}
