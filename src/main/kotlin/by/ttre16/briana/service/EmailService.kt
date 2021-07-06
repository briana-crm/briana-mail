package by.ttre16.briana.service

import by.ttre16.briana.configuration.OUTPUT_CHANNEL
import by.ttre16.briana.model.StreamMessage
import by.ttre16.briana.model.VerificationStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.time.LocalDateTime
import javax.mail.MessagingException

@Service
class EmailService(
    val tokenService: ConfirmationTokenService,
    val mailSender: JavaMailSender,
    val stream: StreamBridge
) {
    @Value("\${email.from}")
    private lateinit var emailFrom: String
    @Value("\${email.subject}")
    lateinit var emailSubject: String
    @Value("\${email.confirmation.url}")
    lateinit var confirmationUrl: String
    @Value("\${email.confirmation.text}")
    lateinit var confirmationText: String
    @Value("\${email.confirmation.successfully}")
    lateinit var confirmationSuccessfully: String

    fun process(email: String) {
        val token = tokenService.create(email).token
        val confirmationLink = UriComponentsBuilder.fromUri(URI(confirmationUrl))
            .queryParam("token", token)
            .build()
            .toString()
        val text = "$confirmationText \n $confirmationLink"
        send(email, text)
    }

    fun send(email: String, text: String) {
        try {
            mailSender.createMimeMessage().let {
                MimeMessageHelper(it, "utf-8").run {
                    setText(text, true)
                    setTo(email)
                    setSubject(emailSubject)
                    setFrom(emailFrom)
                }
                mailSender.send(it)
                val message = StreamMessage(email, VerificationStatus.VERIFICATION_PENDING)
                stream.send(OUTPUT_CHANNEL, message)
            }
        } catch (e: MessagingException) {
            throw IllegalStateException("Failed to send email")
        }
    }

    fun confirm(token: String): String {
        val confirmationToken = tokenService.getToken(token) ?: return "Invalid confirmation link"
        if (confirmationToken.confirmedAt != null) return "Email already confirmed!"
        confirmationToken.expiredAt
            .takeIf { it.isAfter(LocalDateTime.now()) }
            ?: return "Confirmation link expired"
        tokenService.setConfirmedAt(confirmationToken)
        val message = StreamMessage(email = confirmationToken.email, VerificationStatus.VERIFIED)
        stream.send(OUTPUT_CHANNEL, message)
        return confirmationSuccessfully
    }
}
