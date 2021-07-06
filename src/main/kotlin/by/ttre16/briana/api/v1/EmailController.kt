package by.ttre16.briana.api.v1

import by.ttre16.briana.service.EmailService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/email/confirmation")
class EmailController(var emailService: EmailService) {
    @GetMapping
    fun confirm(@RequestParam("token") token: String) = emailService.confirm(token)
}
