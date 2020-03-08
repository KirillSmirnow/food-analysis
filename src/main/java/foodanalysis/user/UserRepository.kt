package foodanalysis.user

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<User, UUID> {

    fun findByTelegramAccountId(id: Long): User?
}
