package foodanalysis.controller

import foodanalysis.MainException
import foodanalysis.file.FileService
import foodanalysis.request.Request
import foodanalysis.request.RequestService
import foodanalysis.user.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/requests")
class RequestController(private val requestService: RequestService,
                        private val userService: UserService,
                        private val fileService: FileService
) {
    @PostMapping
    fun create(@RequestParam clientId: String, @RequestParam text: String?, @RequestParam image: MultipartFile?): Request {
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
}
