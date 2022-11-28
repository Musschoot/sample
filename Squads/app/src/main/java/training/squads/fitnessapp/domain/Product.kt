package training.squads.fitnessapp.domain

import java.time.LocalDateTime
import java.util.UUID

interface Product {
    val id: UUID;
    // var productName: String;
    var paymentStatus: Boolean;
    var purchaseTime: LocalDateTime;
}