package foodanalysis.request

import foodanalysis.MainException
import foodanalysis.request.Request.Companion.ofTelegramImage
import foodanalysis.request.Request.Companion.ofText
import foodanalysis.user.UserService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class RequestServiceImpl(private val requestRepository: RequestRepository,
                         private val rabbitTemplate: RabbitTemplate,
                         private val userService: UserService
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
}
