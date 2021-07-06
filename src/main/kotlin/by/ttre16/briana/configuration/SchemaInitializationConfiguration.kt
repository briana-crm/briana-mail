package by.ttre16.briana.configuration

import by.ttre16.briana.model.ConfirmationTokens
import org.jetbrains.exposed.sql.SchemaUtils
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SchemaInitializationConfiguration: ApplicationListener<ContextRefreshedEvent> {
    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        SchemaUtils.create(ConfirmationTokens)
    }
}
