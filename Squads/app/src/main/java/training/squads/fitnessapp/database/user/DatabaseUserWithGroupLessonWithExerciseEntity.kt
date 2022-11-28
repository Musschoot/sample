package training.squads.fitnessapp.database.user

// import training.squads.fitnessapp.database.grouplesson.DatabaseGroupLessonWithExerciseEntity

/* TODO: Finalize relations
/*
data class DatabaseUserWithGroupLessonWithExercise(
    @Embedded
    val user: DatabaseUserEntity,

    @Relation(
        entity = DatabaseGroupLessonEntity::class,
        parentColumn = "email",
        entityColumn = "emailUser"
    )
    val groupLessons: List<DatabaseGroupLessonWithExerciseEntity>
)
*/