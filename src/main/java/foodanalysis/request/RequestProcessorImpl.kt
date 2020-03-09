package foodanalysis.request

import foodanalysis.MainException
import foodanalysis.analysis.Analyser
import foodanalysis.bot.Bot
import foodanalysis.file.FileService
import foodanalysis.image.ImageToTextConverter
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class RequestProcessorImpl(private val imageToTextConverter: ImageToTextConverter,
                           private val requestRepository: RequestRepository,
                           private val rabbitTemplate: RabbitTemplate,
                           private val fileService: FileService,
                           private val analyser: Analyser,
                           private val bot: Bot
) : RequestProcessor {

    override fun performImageDownloading(requestId: UUID) {
    }

    override fun performOcr(requestId: UUID) {
        val request = getById(requestId)
        val imageSystemId = request.image?.systemId ?: throw MainException("Unable to perform OCR: image ID not found")
        val imageStream = fileService.download(imageSystemId).downloadStream
        val text = imageToTextConverter.convert(imageStream)
        request.text = text
        requestRepository.save(request)
    }

    override fun performAnalysis(requestId: UUID) {
        val request = getById(requestId)
        val text = request.text ?: throw MainException("Unable to perform analysis: text not found")
        val report = analyser.analyse(text)
        request.report = report
        requestRepository.save(request)
    }

    private fun getById(id: UUID): Request {
        return requestRepository.findById(id).orElseThrow { MainException("Request not found: id=$id") }
    }
}
