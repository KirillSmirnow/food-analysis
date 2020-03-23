package foodanalysis.analysis.substance

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface SubstanceRepository : MongoRepository<Substance, UUID>
