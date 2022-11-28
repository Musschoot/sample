package training.squads.fitnessapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Exercise(
    val id: UUID,
    var exerciseName: String,
    var type: String,
    var weight: Double,
    var repetition: Number
): Parcelable