package training.squads.fitnessapp.network

import java.time.LocalDateTime
import java.util.*

data class SubscriptionProperty(
    val id: UUID,
    val purchaseDateTime: LocalDateTime,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val paid: Boolean
)
