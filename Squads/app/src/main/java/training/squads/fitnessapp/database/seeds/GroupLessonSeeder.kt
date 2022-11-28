package training.squads.fitnessapp.database.seeds

import android.content.Context
import androidx.room.Room
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.domain.GroupLesson
import training.squads.fitnessapp.domain.GroupLessonType
import training.squads.fitnessapp.repository.grouplesson.GroupLessonRepository
import java.time.LocalDateTime
import java.util.*

class GroupLessonSeeder {

    var status = true
    private var groupLessons = mutableListOf<GroupLesson>()

    companion object {
        @Volatile
        private var INSTANCE: GroupLessonSeeder? = null

        fun getInstance(): GroupLessonSeeder {
            synchronized(this) {
                var instance = GroupLessonSeeder.INSTANCE

                if (instance == null) {
                    instance = GroupLessonSeeder()
                    GroupLessonSeeder.INSTANCE = instance
                }
                return instance
            }
        }
    }

    fun seedGroupLessons(): List<GroupLesson> {

        val groupLesson1 = GroupLesson(
            UUID.randomUUID(),
            "Mijn eerste yoga",
            GroupLessonType.YOGA,
            "Een inspirerende introductie tot Yoga, waarna je helemaal zen bent",
            LocalDateTime.of(2022, 9, 10, 18, 15, 0),
            LocalDateTime.of(2022, 9, 10, 19, 15, 0),
            6
        )

        val groupLesson2 = GroupLesson(
            UUID.randomUUID(),
            "Mijn eerste training",
            GroupLessonType.TRAINING,
            "Een eerste kennismaking met krachttraining, waarin je je energie kwijt kan",
            LocalDateTime.of(2022, 9, 10, 18, 15, 0),
            LocalDateTime.of(2022, 9, 10, 19, 15, 0),
            6
        )

        val groupLesson3 = GroupLesson(
            UUID.randomUUID(),
            "Yoga met An Oniem",
            GroupLessonType.YOGA,
            "An Oniem heeft een jarenlange ervaring met yoga achter de rug. Krijg tips van haar om je yoga sessie naar een hoger niveau te tillen",
            LocalDateTime.of(2022, 10, 11, 18, 15, 0),
            LocalDateTime.of(2022, 10, 11, 19, 15, 0),
            6
        )

        val groupLesson4 = GroupLesson(
            UUID.randomUUID(),
            "Krachttraining met Pat Sers",
            GroupLessonType.TRAINING,
            "Pat geeft al 20 jaar cursussen gewichtheffen. Ontdek hoe je jouw spiermassa op de effectiefste manier kan vergroten",
            LocalDateTime.of(2022, 10, 11, 18, 15, 0),
            LocalDateTime.of(2022, 10, 11, 19, 15, 0),
            6
        )

        groupLessons.add(groupLesson1)
        groupLessons.add(groupLesson2)
        groupLessons.add(groupLesson3)
        groupLessons.add(groupLesson4)

        return groupLessons.toList()
    }
}