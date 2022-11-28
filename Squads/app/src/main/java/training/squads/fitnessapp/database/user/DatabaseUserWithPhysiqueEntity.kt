package training.squads.fitnessapp.database.user

import androidx.room.Embedded
import androidx.room.Relation
import training.squads.fitnessapp.database.physique.DatabasePhysiqueEntity

data class DatabaseUserWithPhysiqueEntity(
    @Embedded val user: DatabaseUserEntity,
    @Relation(
        parentColumn = "email",
        entityColumn = "userEmail"
    )
    val physique: DatabasePhysiqueEntity
)