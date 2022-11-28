package training.squads.fitnessapp.network

import training.squads.fitnessapp.database.pass.DatabasePassEntity
import java.time.LocalDateTime
import java.util.*

data class SessionPassProperty(
    val id: UUID,
    val userId: UUID,
    val purchaseDateTime: LocalDateTime,
    val turns: Int,
    val paid: Boolean
) {

    fun asDatabasePass(): DatabasePassEntity {
        return DatabasePassEntity(
            id = id.toString(),
            userId = userId.toString(),
            purchaseTime = purchaseDateTime.toString(),
            sessions = turns,
            paymentStatus = paid
        )
    }

}
