package training.squads.fitnessapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import training.squads.fitnessapp.database.grouplesson.DatabaseGroupLessonEntity
import training.squads.fitnessapp.database.grouplesson.dateConverter
import training.squads.fitnessapp.database.grouplesson.gltConverter
import training.squads.fitnessapp.database.grouplesson.idConverter
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class GroupLesson(
    val id: UUID,
    val title: String,
    val type: GroupLessonType,
    val description: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    var totalParticipants: Int,
    // var exercises: List<Exercise>,
    // var comments: List<Comment>
): Parcelable

/**
 * Map GroupLesson (domain) to GroupLesson (database)
 */
fun GroupLesson.asDatabaseGroupLessonModel(): DatabaseGroupLessonEntity {
    return DatabaseGroupLessonEntity(
            id = idConverter.fromUuid(id),
            lessonName = title,
            lessonType = gltConverter.fromGroupLessonType(type),
            description = description,
            startTime = dateConverter.toJson(startDateTime),
            endTime = dateConverter.toJson(endDateTime),
            totalParticipants = totalParticipants
        )
}

/**
 * Map list of GroupLesson (domain) to list of GroupLesson (database)
 */
fun List<GroupLesson>.asDatabaseGroupLessonListModel(): List<DatabaseGroupLessonEntity> {
    return map {
        DatabaseGroupLessonEntity(
        id = idConverter.fromUuid(it.id),
        lessonName = it.title,
        lessonType = gltConverter.fromGroupLessonType(it.type),
        description = it.description,
        startTime = dateConverter.toJson(it.startDateTime),
        endTime = dateConverter.toJson(it.endDateTime),
        totalParticipants = it.totalParticipants
        )
    }
}