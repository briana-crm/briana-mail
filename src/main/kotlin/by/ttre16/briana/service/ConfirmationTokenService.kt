package by.ttre16.briana.service

import by.ttre16.briana.model.ConfirmationToken
import by.ttre16.briana.model.ConfirmationTokens
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class ConfirmationTokenService(
    @Value("\${email.confirmation.expired.minutes}")
    val expiredMinutes: Long
) {
    fun getToken(token: String) = ConfirmationToken
        .find { ConfirmationTokens.token eq token }
        .firstOrNull()

    fun setConfirmedAt(confirmationToken: ConfirmationToken) {
        confirmationToken.confirmedAt = LocalDateTime.now()
    }

    fun create(email: String): ConfirmationToken = ConfirmationToken.new {
        this.email = email
        createdAt = LocalDateTime.now()
        expiredAt = LocalDateTime.now().plusMinutes(expiredMinutes)
        token = UUID.randomUUID().toString()
    }
}
