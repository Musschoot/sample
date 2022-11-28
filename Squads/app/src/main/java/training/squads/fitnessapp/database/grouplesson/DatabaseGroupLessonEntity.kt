package training.squads.fitnessapp.database.grouplesson

import android.util.Log
import androidx.lifecycle.Transformations.map
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import training.squads.fitnessapp.database.BaseEntity
import training.squads.fitnessapp.domain.GroupLesson
import training.squads.fitnessapp.network.jsonAdapters.localDateTimeJsonAdapter
import training.squads.fitnessapp.utilities.GroupLessonTypeConverter
import training.squads.fitnessapp.utilities.uuidConverter

val dateConverter: localDateTimeJsonAdapter = localDateTimeJsonAdapter()
val gltConverter: GroupLessonTypeConverter = GroupLessonTypeConverter()
val idConverter: uuidConverter = uuidConverter()

/**
 * Database entity for group lesson
 * This represents a group lesson in the database
 */
@Entity(tableName = "group_lesson")
data class DatabaseGroupLessonEntity(
    @PrimaryKey
    @ColumnInfo(name = "lesson_id")
    override
    val id: String,

    @ColumnInfo(name = "name")
    var lessonName: String,

    // TODO: Finalize relations
    // var emailUser: String,

    @ColumnInfo(name = "type")
    var lessonType: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "start")
    var startTime: String,

    @ColumnInfo(name = "end")
    var endTime: String,

    @ColumnInfo(name = "participants")
    var totalParticipants: Int
): BaseEntity()

/**
 * Map GroupLesson (database) to Grouplesson (domain)
 */

fun List<DatabaseGroupLessonEntity>.asDomainModel(): List<GroupLesson> {

    return map {
        Log.i("Database status test lesson type", it.lessonType)
        GroupLesson(
            id = idConverter.toUuid(it.id),
            title = it.lessonName,
            type = gltConverter.toGroupLessonType(it.lessonType),
            description = it.description,
            startDateTime = dateConverter.fromJson(it.startTime),
            endDateTime = dateConverter.fromJson(it.endTime),
            totalParticipants = it.totalParticipants,
            // exercises = listOf(),
            // comments = listOf()
        )
    }
}


/*
fun DatabaseGroupLessonEntity.asDomainModel(): GroupLesson {
    return GroupLesson(
        id = idConverter.toUuid(id),
        title = lessonName,
        type = gltConverter.toGroupLessonType(lessonType),
        description = description,
        startDateTime = dateConverter.toDate(startTime),
        endDateTime = dateConverter.toDate(endTime),
        totalParticipants = totalParticipants,
    )
}
*/