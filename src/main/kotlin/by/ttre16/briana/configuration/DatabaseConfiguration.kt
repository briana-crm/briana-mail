package by.ttre16.briana.configuration

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class DatabaseConfiguration {
    @Bean
    @Primary
    fun transactionManager(dataSource: DataSource) = SpringTransactionManager(dataSource)
}
