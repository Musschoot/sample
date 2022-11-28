package training.squads.fitnessapp.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import training.squads.fitnessapp.database.BaseEntity
import training.squads.fitnessapp.database.pass.DatabaseDaoPassEntity
import training.squads.fitnessapp.database.pass.DatabasePassEntity
import training.squads.fitnessapp.database.pass.asPassListDomainModel
import training.squads.fitnessapp.domain.Pass
import training.squads.fitnessapp.domain.User
import training.squads.fitnessapp.utilities.DateConverter
import training.squads.fitnessapp.utilities.GroupLessonTypeConverter
import training.squads.fitnessapp.utilities.uuidConverter

val dateConverter: DateConverter = DateConverter()
val idConverter: uuidConverter = uuidConverter()

@Entity(tableName = "user")
data class DatabaseUserEntity(
    @ColumnInfo(name = "user_id")
    override
    val id: String,

    @ColumnInfo(name = "first_name")
    var firstName: String,

    @ColumnInfo(name = "last_name")
    var lastName: String,

    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "created_on")
    var createdOn: String,

    @ColumnInfo(name = "birthday")
    var birthDay: String,

    @ColumnInfo(name = "role")
    val role: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "address")
    val street: String,

    @ColumnInfo(name = "house_number")
    val houseNumber: Int,

    @ColumnInfo(name = "location")
    var location: String,

    @ColumnInfo(name = "postal_code")
    val postalCode: Int,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "complaints")
    var complaints: String,

    @ColumnInfo(name = "medicine")
    var medicine: String,

    @ColumnInfo(name = "newsletter")
    val newsletter: Boolean,

    @ColumnInfo(name = "whatsapp")
    val whatsapp: Boolean,

    @ColumnInfo(name = "house_rules")
    val houseRules: Boolean,

    @ColumnInfo(name = "sessions_left")
    var sessionsLeft: Int,

    @ColumnInfo(name = "trial")
    val trial: Boolean

): BaseEntity()
