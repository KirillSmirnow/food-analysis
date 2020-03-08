package foodanalysis.request

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface RequestRepository : MongoRepository<Request, UUID>
