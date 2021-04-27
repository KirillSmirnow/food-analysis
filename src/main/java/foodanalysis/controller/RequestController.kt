package foodanalysis.controller

import foodanalysis.MainException
import foodanalysis.file.FileService
import foodanalysis.request.Request
import foodanalysis.request.RequestService
import foodanalysis.user.UserService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/requests")
class RequestController(
    private val requestService: RequestService,
    private val userService: UserService,
    private val fileService: FileService
) {
    @PostMapping
    fun create(
        @RequestParam clientId: String,
        @RequestParam text: String?,
        @RequestParam image: MultipartFile?
    ): Request {
        userService.authenticate(clientId)
        if (!text.isNullOrBlank()) {
            return requestService.createOfText(text)
        }
        if (image != null && !image.isEmpty) {
            val imageInfo = fileService.upload(image.inputStream, "")
            return requestService.createOfImage(imageInfo.id)
        }
        throw MainException("You must provide text or image")
    }

    @GetMapping("/{id}")
    fun getByRequestId(@PathVariable id: UUID): Request = requestService.getById(id)

    @GetMapping
    fun getByClientId(@RequestParam clientId: String, @RequestParam page: Int): Page<Request> {
        userService.authenticate(clientId)
        return requestService.getByCurrentUser(page)
    }
}
