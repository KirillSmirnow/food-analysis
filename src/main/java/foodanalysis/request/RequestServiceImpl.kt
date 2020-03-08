package foodanalysis.request

import foodanalysis.MainException
import foodanalysis.analysis.Analyser
import foodanalysis.file.FileService
import foodanalysis.image.ImageToTextConverter
import foodanalysis.request.Request.Companion.ofTelegramImage
import foodanalysis.request.Request.Companion.ofText
import foodanalysis.user.UserService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class RequestServiceImpl(private val imageToTextConverter: ImageToTextConverter,
                         private val requestRepository: RequestRepository,
                         private val rabbitTemplate: RabbitTemplate,
                         private val userService: UserService,
                         private val fileService: FileService,
                         private val analyser: Analyser
) : RequestService {

    override fun getById(id: UUID): Request {
        return requestRepository.findById(id).orElseThrow { MainException("Request not found: id=$id") }
    }

    override fun createOfText(text: String) {
        val user = userService.getAuthenticatedUser()
        val request = requestRepository.save(ofText(user.id, text))
        rabbitTemplate.convertAndSend("performAnalysis", request.id)
    }

    override fun createOfTelegramImage(imageId: String) {
        val user = userService.getAuthenticatedUser()
        val request = requestRepository.save(ofTelegramImage(user.id, imageId))
        rabbitTemplate.convertAndSend("performImageDownloading", request.id)
    }

    override fun performImageDownloading(requestId: UUID) {
        throw NotImplementedError()
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
}
