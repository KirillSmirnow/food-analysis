package foodanalysis.user

interface UserService {

    fun authenticate(telegramAccount: TelegramAccount)

    fun getAuthenticatedUser(): User
}
