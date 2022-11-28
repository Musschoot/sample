package training.squads.fitnessapp.database.seeds

import training.squads.fitnessapp.domain.GroupLesson
import training.squads.fitnessapp.domain.GroupLessonType
import training.squads.fitnessapp.domain.Pass
import training.squads.fitnessapp.domain.User
import java.time.LocalDateTime
import java.util.*

class UserSeeder {

    var status = true
    private var users = mutableListOf<User>()

    companion object {
        @Volatile
        private var INSTANCE: UserSeeder? = null

        fun getInstance(): UserSeeder {
            synchronized(this) {
                var instance = UserSeeder.INSTANCE

                if (instance == null) {
                    instance = UserSeeder()
                    UserSeeder.INSTANCE = instance
                }
                return instance
            }
        }
    }

    fun seedUsers(): List<User> {

        val user1 = User(
            UUID.fromString("e583748d-9039-4011-8430-0f37b4742595"),
            "fe.vanmanshoven@hotmail.com",
            "admin",
            "active",
            "Fe",
            "Vanmanshoven",
            LocalDateTime.of(1997,11,22,16,8,43),
            "Voormuide",
            20,
            "Gent",
            9000,
            "0487148450",
            true,
            true,
            true,
            listOf(),
            listOf(
                Pass(
                    UUID.fromString("08dacda7-91b7-4b73-847f-7e6f3c66f056"),
                    false,
                    LocalDateTime.of(2022,11,24,0,0,0),
                    10
                ),
                Pass(
                    UUID.fromString("08dace6b-8eca-482e-8f3c-d96454306081"),
                    true,
                    LocalDateTime.of(2022,11,24,22,30,38),
                    50
                )
            ),
            listOf(),
            61,
            // trial
        )

        users.add(user1)

        return users.toList()
    }
}