package training.squads.fitnessapp.utilities

import androidx.room.TypeConverter
import training.squads.fitnessapp.domain.ExerciseType

class ExerciseTypeConverter {
    fun toExerciseType(value: String) = enumValueOf<ExerciseType>(value)

    @TypeConverter
    fun fromExerciseType(value: ExerciseType) = value.name
}