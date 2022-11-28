package training.squads.fitnessapp.domain

enum class GroupLessonType(/*type: String*/val value: Int) {
    //TRAINING("TRAINING"), YOGA("YOGA")

    TRAINING(1),
    YOGA(2);

    companion object {
        fun fromValueOrNull(value: Int): /*Session*/GroupLessonType? {
            return /*Session*/GroupLessonType.values().firstOrNull { it.value == value }
        }
    }
}