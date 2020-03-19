package foodanalysis.request

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface RequestRepository : MongoRepository<Request, UUID> {

    fun findByUserId(userId: UUID, pageable: Pageable): Page<Request>
}
