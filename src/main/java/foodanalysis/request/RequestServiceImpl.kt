package foodanalysis.request

import foodanalysis.MainException
import foodanalysis.request.Request.Companion.ofImage
import foodanalysis.request.Request.Companion.ofTelegramImage
import foodanalysis.request.Request.Companion.ofText
import foodanalysis.user.UserService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class RequestServiceImpl(
    private val requestRepository: RequestRepository,
    private val rabbitTemplate: RabbitTemplate,
    private val userService: UserService
) : RequestService {

    override fun getById(id: UUID): Request {
        return requestRepository.findById(id).orElseThrow { MainException("Request not found: id=$id") }
    }

    override fun getByCurrentUser(page: Int): Page<Request> {
        val user = userService.getAuthenticatedUser()
        val pageable = PageRequest.of(page, 10, Sort.by("createdOn").descending())
        return requestRepository.findByUserId(user.id, pageable)
    }

    override fun createOfText(text: String): Request {
        val user = userService.getAuthenticatedUser()
        val request = requestRepository.save(ofText(user.id, text))
        rabbitTemplate.convertAndSend("performAnalysis", request.id)
        return request
    }

    override fun createOfImage(imageId: String): Request {
        val user = userService.getAuthenticatedUser()
        val request = requestRepository.save(ofImage(user.id, imageId))
        rabbitTemplate.convertAndSend("performOcr", request.id)
        return request
    }

    override fun createOfTelegramImage(imageId: String): Request {
        val user = userService.getAuthenticatedUser()
        val request = requestRepository.save(ofTelegramImage(user.id, imageId))
        rabbitTemplate.convertAndSend("performImageDownloading", request.id)
        return request
    }
}
