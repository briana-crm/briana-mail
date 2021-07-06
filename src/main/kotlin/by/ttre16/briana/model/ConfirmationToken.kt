package by.ttre16.briana.model

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.util.*

object ConfirmationTokens: UUIDTable("confirmation_tokens") {
    val email = varchar("email", 128)
    val token = varchar("login", 128)
    var createdAt = datetime("created_at")
    var expiredAt = datetime("expired_at")
    var confirmedAt = datetime("confirmed_at").nullable()
}

class ConfirmationToken(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<ConfirmationToken>(ConfirmationTokens)
    var email by ConfirmationTokens.email
    var token by ConfirmationTokens.token
    var createdAt by ConfirmationTokens.createdAt
    var expiredAt by ConfirmationTokens.expiredAt
    var confirmedAt by ConfirmationTokens.confirmedAt
}

