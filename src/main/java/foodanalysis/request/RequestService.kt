package foodanalysis.request

interface RequestService {

    fun createOfText(text: String)

    fun createOfTelegramImage(imageId: String)
}
