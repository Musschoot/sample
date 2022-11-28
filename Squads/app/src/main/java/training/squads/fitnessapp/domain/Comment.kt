package training.squads.fitnessapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class Comment(
    val id: UUID,
    var title: String,
    var description: String,
    var timestamp: Date
): Parcelable