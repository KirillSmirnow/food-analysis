package foodanalysis.request

import org.springframework.amqp.rabbit.annotation.RabbitListener
import java.util.*

interface RequestService {

    fun getById(id: UUID): Request

    fun createOfText(text: String)

    fun createOfTelegramImage(imageId: String)

    @RabbitListener(queues = ["performImageDownloading"])
    fun performImageDownloading(requestId: UUID)

    @RabbitListener(queues = ["performOcr"])
    fun performOcr(requestId: UUID)

    @RabbitListener(queues = ["performAnalysis"])
    fun performAnalysis(requestId: UUID)
}
