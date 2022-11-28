package training.squads.fitnessapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import training.squads.fitnessapp.database.grouplesson.DatabaseDaoGroupLesson
import training.squads.fitnessapp.database.grouplesson.DatabaseGroupLessonEntity
import training.squads.fitnessapp.database.pass.DatabaseDaoPassEntity
import training.squads.fitnessapp.database.pass.DatabasePassEntity
import training.squads.fitnessapp.database.user.DatabaseDaoUser
import training.squads.fitnessapp.database.user.DatabaseUserEntity
import training.squads.fitnessapp.utilities.DateConverter
import training.squads.fitnessapp.utilities.ExerciseTypeConverter
import training.squads.fitnessapp.utilities.GroupLessonTypeConverter
import training.squads.fitnessapp.utilities.uuidConverter

/**
 * Room database instance for the group lessons
 */
@Database(entities = [DatabaseGroupLessonEntity::class, DatabaseUserEntity::class, DatabasePassEntity::class], version = 5, exportSchema = false)
@TypeConverters(DateConverter::class, uuidConverter::class, ExerciseTypeConverter::class)
abstract class DatabaseSquads: RoomDatabase() {
    abstract val databaseDaoGroupLesson: DatabaseDaoGroupLesson
    abstract val databaseDaoUser: DatabaseDaoUser
    abstract val databaseDaoPass: DatabaseDaoPassEntity

    companion object {
        @Volatile
        private var INSTANCE: DatabaseSquads? = null

        fun getInstance(context: Context): DatabaseSquads {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseSquads::class.java,
                        "squads_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
