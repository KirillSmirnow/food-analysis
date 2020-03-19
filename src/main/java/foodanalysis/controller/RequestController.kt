package foodanalysis.controller

import foodanalysis.request.Request
import foodanalysis.request.RequestService
import foodanalysis.user.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID.randomUUID

@RestController
@RequestMapping("/requests")
class RequestController(private val userService: UserService,
                        private val requestService: RequestService
) {
    @PostMapping
    fun create(@RequestParam clientId: String, @RequestParam text: String?, @RequestParam image: MultipartFile?): Request {
        return Request.ofText(randomUUID(), "Test")
    }
}
