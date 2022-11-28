package training.squads.fitnessapp.network

import training.squads.fitnessapp.database.grouplesson.DatabaseGroupLessonEntity
import training.squads.fitnessapp.domain.GroupLessonType
import training.squads.fitnessapp.utilities.GroupLessonTypeConverter
import java.time.LocalDateTime
import java.util.*

data class SessionProperty(
    val id: UUID,
    val title : String,
    val description: String,
    val sessionType: GroupLessonType,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val users: List<UserProperty>
){
    fun asDatabaseGroupLesson(): DatabaseGroupLessonEntity {
        return DatabaseGroupLessonEntity(
            id = id.toString(),
            lessonName = title,
            lessonType = sessionType.toString(),
            description = description,
            startTime = startDateTime.toString(),
            endTime = endDateTime.toString(),
            totalParticipants = 0
        )}
}
