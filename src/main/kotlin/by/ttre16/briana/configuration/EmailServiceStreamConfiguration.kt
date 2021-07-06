package by.ttre16.briana.configuration

import by.ttre16.briana.service.EmailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import java.util.function.Consumer

const val OUTPUT_CHANNEL: String = "email-confirmation-out-0"

@Configuration
@PropertySource("classpath:mail/mail.properties")
class EmailServiceStreamConfiguration {
    @Bean
    fun process(emailService: EmailService): Consumer<String> {
        return Consumer { str -> emailService.process(str) }
    }
}
