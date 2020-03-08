package foodanalysis.user

import foodanalysis.MainException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun authenticate(telegramAccount: TelegramAccount) {
        val user = userRepository.findByTelegramAccountId(telegramAccount.id)
                ?: userRepository.save(User.ofTelegramAccount(telegramAccount))
        SecurityContextHolder.getContext().authentication = UserAuthentication(user)
    }

    override fun getAuthenticatedUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication is UserAuthentication) {
            return authentication.principal
        }
        throw MainException("User not authenticated")
    }
}
