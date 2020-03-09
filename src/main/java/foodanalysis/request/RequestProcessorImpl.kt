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
        val request = getById(requestId)
        val imageTelegramId = request.image?.telegramId ?: throw MainException("No image Telegram ID")
        val imageStream = bot.fetchFile(imageTelegramId)
        val imageInfo = fileService.upload(imageStream, "")
        request.image.systemId = imageInfo.id
        requestRepository.save(request)
        rabbitTemplate.convertAndSend("performOcr", request.id)
    }

    override fun performOcr(requestId: UUID) {
        val request = getById(requestId)
        val imageSystemId = request.image?.systemId ?: throw MainException("No image system ID")
        val image = fileService.download(imageSystemId)
        val text = imageToTextConverter.convert(image.downloadStream)
        request.text = text
        requestRepository.save(request)
        rabbitTemplate.convertAndSend("performAnalysis", request.id)
    }

    override fun performAnalysis(requestId: UUID) {
        val request = getById(requestId)
        val text = request.text ?: throw MainException("No text")
        val report = analyser.analyse(text)
        request.report = report
        requestRepository.save(request)
    }

    private fun getById(id: UUID): Request {
        return requestRepository.findById(id).orElseThrow { MainException("Request not found") }
    }
}
