package training.squads.fitnessapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import training.squads.fitnessapp.database.grouplesson.DatabaseGroupLessonEntity
import training.squads.fitnessapp.database.grouplesson.gltConverter
import training.squads.fitnessapp.database.grouplesson.idConverter
import training.squads.fitnessapp.database.user.DatabaseUserEntity
import training.squads.fitnessapp.utilities.DateConverter
import java.time.LocalDateTime
import java.util.*

private var dateConverter = DateConverter()

@Parcelize
data class User(
    val id: UUID,
    var email: String,
    var role: String,
    var status: String,
    var firstName: String,
    var lastName: String,
    var birthDay: LocalDateTime,
    var street: String,
    var houseNumber: Int,
    var location: String,
    var postalCode: Int,
    var phone: String,
    var newsletter: Boolean,
    var whatsapp: Boolean,
    var houseRules: Boolean,
    var subscriptions: List<Subscription>,
    var passes: List<Pass>,
    var groupLessons: List<GroupLesson>,
    var sessionsLeft: Int,
    // var trial: Boolean
): Parcelable


fun List<User>.asDatabaseUserListModel(): List<DatabaseUserEntity> {
    return map {
        DatabaseUserEntity(
            id = idConverter.fromUuid(it.id),
            firstName = it.firstName,
            lastName = it.lastName,
            email = it.email,
            createdOn = "",
            birthDay = dateConverter.fromDate(it.birthDay),
            role = it.role,
            status = "",
            street = it.street,
            houseNumber = it.houseNumber,
            location = it.location,
            postalCode = it.postalCode,
            phone = it.phone,
            complaints = "",
            medicine = "",
            newsletter = it.newsletter,
            whatsapp = it.whatsapp,
            houseRules = it.houseRules,
            sessionsLeft = it.sessionsLeft,
            trial = false
        )
    }
}