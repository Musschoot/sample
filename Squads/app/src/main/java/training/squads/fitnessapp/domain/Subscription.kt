package training.squads.fitnessapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class Subscription (
    override val id: UUID,
    // override var productName: String,
    override var paymentStatus: Boolean,
    override var purchaseTime: LocalDateTime,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime
): Product, Parcelable