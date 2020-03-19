package foodanalysis.request

import java.util.*

interface RequestService {

    fun getById(id: UUID): Request

    fun createOfText(text: String): Request

    fun createOfImage(imageId: String): Request

    fun createOfTelegramImage(imageId: String): Request
}
