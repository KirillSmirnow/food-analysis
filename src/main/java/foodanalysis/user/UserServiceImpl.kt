package foodanalysis.user

import foodanalysis.MainException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun authenticate(clientId: String) {
        val user = userRepository.findByClientId(clientId)
                ?: userRepository.save(User.ofClientId(clientId))
        authenticate(user)
    }

    override fun authenticate(telegramAccount: TelegramAccount) {
        val user = userRepository.findByTelegramAccountId(telegramAccount.id)
                ?: userRepository.save(User.ofTelegramAccount(telegramAccount))
        authenticate(user)
    }

    private fun authenticate(user: User) {
        SecurityContextHolder.getContext().authentication = UserAuthentication(user)
    }

    override fun getAuthenticatedUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication is UserAuthentication) {
            return authentication.principal
        }
        throw MainException("User not authenticated")
    }

    override fun getById(id: UUID): User {
        return userRepository.findById(id).orElseThrow { MainException("User not found") }
    }
}
