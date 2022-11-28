package training.squads.fitnessapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class Pass(
    override val id: UUID,
    // override var productName: String,
    override var paymentStatus: Boolean,
    override var purchaseTime: LocalDateTime,
    var sessions: Int
): Product, Parcelable