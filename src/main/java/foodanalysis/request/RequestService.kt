package foodanalysis.request

import org.springframework.data.domain.Page
import java.util.*

interface RequestService {

    fun getById(id: UUID): Request

    fun getByCurrentUser(page: Int): Page<Request>

    fun createOfText(text: String): Request

    fun createOfImage(imageId: String): Request

    fun createOfTelegramImage(imageId: String): Request
}
