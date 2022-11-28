package training.squads.fitnessapp.database.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import training.squads.fitnessapp.database.BaseDao

// TODO: Finalize relations
@Dao
abstract class DatabaseDaoUser: BaseDao<DatabaseUserEntity>() {

    /*
    @Transaction
    @Query("SELECT * FROM user")
    abstract fun getUsersWithGroupLessonsWithExercises(): List<DatabaseUserWithGroupLessonWithExerciseEntity>

    @Transaction
    @Query("SELECT * FROM user")
    abstract fun getUsersWithGroupLessonsWithComments(): List<DatabaseUserWithGroupLessonWithCommentEntity>

    @Transaction
    @Query("SELECT * FROM user")
    abstract fun getUsersWithPhysique(): List<DatabaseUserWithPhysiqueEntity>
    */

    @Query("DELETE FROM user")
    abstract fun clear()

    @Transaction
    @Query("SELECT * FROM user WHERE email = :key")
    abstract fun getUserWithPassesLive(key: String): LiveData<DatabaseUserWithPassesEntity>

}
