package foodanalysis.request

import java.util.*

interface RequestService {

    fun getById(id: UUID): Request

    fun createOfText(text: String)

    fun createOfTelegramImage(imageId: String)
}
