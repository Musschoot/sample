package training.squads.fitnessapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Physique(
    var disease: Array<String>,
    var medication: String,
    var height: Double,
    var weight: Double,
    var fat: Double,
    var waistCircumference: Double
): Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Physique

        if (!disease.contentEquals(other.disease)) return false
        if (medication != other.medication) return false
        if (height != other.height) return false
        if (weight != other.weight) return false
        if (fat != other.fat) return false
        if (waistCircumference != other.waistCircumference) return false

        return true
    }

    override fun hashCode(): Int {
        var result = disease.contentHashCode()
        result = 31 * result + medication.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + fat.hashCode()
        result = 31 * result + waistCircumference.hashCode()
        return result
    }
}