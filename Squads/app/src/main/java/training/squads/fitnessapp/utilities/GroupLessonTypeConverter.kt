package training.squads.fitnessapp.utilities

import androidx.room.TypeConverter
import training.squads.fitnessapp.domain.GroupLessonType

/**
 * Converts group lesson types to strings and vice versa
 */
class GroupLessonTypeConverter {

    fun toGroupLessonType(value: String): GroupLessonType {
        var type: GroupLessonType = GroupLessonType.TRAINING
        when(value) {
            "TRAINING" -> type = GroupLessonType.TRAINING
            "YOGA" -> type = GroupLessonType.YOGA
        }
        return type
    }

    fun fromGroupLessonType(value: GroupLessonType): String {
        var type: String
        when(value) {
            GroupLessonType.TRAINING -> type = "Training"
            GroupLessonType.YOGA -> type = "Yoga"
        }
        return type
    }

}