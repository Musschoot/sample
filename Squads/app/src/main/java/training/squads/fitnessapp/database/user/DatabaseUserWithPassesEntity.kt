package training.squads.fitnessapp.database.user

import androidx.room.Embedded
import androidx.room.Relation
import training.squads.fitnessapp.database.pass.DatabasePassEntity
import training.squads.fitnessapp.database.pass.asPassListDomainModel
import training.squads.fitnessapp.domain.User

data class DatabaseUserWithPassesEntity (
    @Embedded
    val user: DatabaseUserEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "userId"
    )
    val passes: List<DatabasePassEntity>
)

fun DatabaseUserWithPassesEntity.asUserWithPassesDomainModel(): User {
    return User(
        id = idConverter.toUuid(user.id),
        email = user.email,
        role = "",
        status = "",
        firstName = user.firstName,
        lastName =  user.lastName,
        birthDay = dateConverter.toDate(user.birthDay),
        street = user.street,
        houseNumber = user.houseNumber,
        location = user.location,
        postalCode = user.postalCode,
        phone = user.phone,
        newsletter = user.newsletter,
        whatsapp = user.whatsapp,
        houseRules = user.houseRules,
        subscriptions = emptyList(),
        passes = passes.asPassListDomainModel(),
        groupLessons = emptyList(),
        sessionsLeft = user.sessionsLeft
    )
}