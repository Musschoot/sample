package training.squads.fitnessapp.database.user

// import training.squads.fitnessapp.database.grouplesson.DatabaseGroupLessonWithCommentEntity

/* TODO: Finalize relations
/*
data class DatabaseUserWithGroupLessonWithComment(
    @Embedded
    val user: DatabaseUserEntity,

    @Relation(
        entity = DatabaseGroupLessonEntity::class,
        parentColumn = "email",
        entityColumn = "emailUser"
    )
    val groupLessons: List<DatabaseGroupLessonWithCommentEntity>
)
*/