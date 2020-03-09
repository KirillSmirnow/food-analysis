package foodanalysis.request

import org.springframework.amqp.rabbit.annotation.RabbitListener
import java.util.*

interface RequestProcessor {

    @RabbitListener(queues = ["performImageDownloading"])
    fun performImageDownloading(requestId: UUID)

    @RabbitListener(queues = ["performOcr"])
    fun performOcr(requestId: UUID)

    @RabbitListener(queues = ["performAnalysis"])
    fun performAnalysis(requestId: UUID)
}
