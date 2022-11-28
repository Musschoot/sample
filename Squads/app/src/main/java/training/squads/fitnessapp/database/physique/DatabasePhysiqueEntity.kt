package training.squads.fitnessapp.database.physique

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import training.squads.fitnessapp.database.BaseEntity

@Entity(tableName = "physique")
data class DatabasePhysiqueEntity(
    @PrimaryKey
    @ColumnInfo(name = "physique_id")
    @Json(name = "physique_id")
    override
    val id: String,

    val userEmail: String,

    @ColumnInfo(name = "diseases")
    @Json(name = "diseases")
    var diseases: Array<String>,

    @ColumnInfo(name = "medications")
    @Json(name = "medications")
    var medications: Array<String>,

    @ColumnInfo(name = "height")
    @Json(name = "height")
    var height: Double,

    @ColumnInfo(name = "weight")
    @Json(name = "weight")
    var weight: Double,

    @ColumnInfo(name = "fat")
    @Json(name = "fat")
    var fat: Double,

    @ColumnInfo(name = "waist_circumference")
    @Json(name = "waist_circumference")
    var waistCircumference: Double

): BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DatabasePhysiqueEntity

        if (id != other.id) return false
        if (!diseases.contentEquals(other.diseases)) return false
        if (!medications.contentEquals(other.medications)) return false
        if (height != other.height) return false
        if (weight != other.weight) return false
        if (fat != other.fat) return false
        if (waistCircumference != other.waistCircumference) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + diseases.contentHashCode()
        result = 31 * result + medications.contentHashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + fat.hashCode()
        result = 31 * result + waistCircumference.hashCode()
        return result
    }
}