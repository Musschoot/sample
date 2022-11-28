package training.squads.fitnessapp.network

import training.squads.fitnessapp.database.pass.DatabasePassEntity
import training.squads.fitnessapp.database.user.DatabaseUserEntity
import training.squads.fitnessapp.database.user.DatabaseUserWithPassesEntity
import training.squads.fitnessapp.network.SessionPassProperty
import java.time.LocalDateTime
import java.util.*

data class UserProperty(
    val id: UUID,
    val firstName : String,
    val lastName : String,
    val email : String,
    val createdOn: LocalDateTime,
    val dateOfBirth: LocalDateTime,
    val street: String,
    val houseNumber : Int,
    val city : String,
    val postalCode: Int,
    val phoneNumber : String,
    val complaints : String,
    val medicine : String,
    val newsLetterAgreement : Boolean,
    val whatsAppAgreement : Boolean,
    val houseRulesAgreement : Boolean,
    val subscriptions: List<SubscriptionProperty>,
    val sessionPasses: List<SessionPassProperty>,
    val sessions: List<SessionProperty>,
    val turnsLeft: Int
) {

    fun asDatabaseUser(): DatabaseUserEntity {
        return DatabaseUserEntity(
            id = id.toString(),
            createdOn = dateOfBirth.toString(), // TODO --> in api .Net
            email = email,
            role = "",
            status = "",
            firstName = firstName,
            lastName =  lastName,
            birthDay = dateOfBirth.toString(),
            street = street,
            houseNumber = houseNumber,
            location = city,
            postalCode = postalCode,
            phone = phoneNumber,
            complaints = complaints,
            medicine = medicine,
            trial = false,
            newsletter = newsLetterAgreement,
            whatsapp = whatsAppAgreement,
            houseRules = houseRulesAgreement,
            sessionsLeft = turnsLeft
        )
    }

    fun List<SessionPassProperty>.asDatabasePassesList(): List<DatabasePassEntity> {
        return map {
            DatabasePassEntity(
                id = it.id.toString(),
                userId = it.userId.toString(),
                paymentStatus = it.paid,
                purchaseTime = it.purchaseDateTime.toString(),
                sessions = it.turns
            )
        }
    }

    fun asDatabaseUserWithPasses(): DatabaseUserWithPassesEntity {
        return DatabaseUserWithPassesEntity(
            user = asDatabaseUser(),
            passes = sessionPasses.asDatabasePassesList()
        )
    }
}