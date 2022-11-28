package training.squads.fitnessapp.database.grouplesson

import androidx.room.Embedded
import androidx.room.Relation
// import training.squads.fitnessapp.database.comment.DatabaseCommentEntity

/* TODO: Finalize relations
/*
data class DatabaseGroupLessonWithCommentEntity(
    @Embedded
    val groupLesson: DatabaseGroupLessonEntity,

    @Relation(
        parentColumn = "lesson_id",
        entityColumn = "groupLessonId"
    )
    val comments: List<DatabaseCommentEntity>
)
*/