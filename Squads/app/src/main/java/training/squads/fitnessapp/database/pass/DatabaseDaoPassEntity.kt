package training.squads.fitnessapp.database.pass

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomDatabase.QueryCallback
import training.squads.fitnessapp.database.BaseDao
import training.squads.fitnessapp.database.grouplesson.DatabaseGroupLessonEntity

@Dao
abstract class DatabaseDaoPassEntity: BaseDao<DatabasePassEntity>() {

    @Query("SELECT * FROM passes ORDER BY purchase_time ASC")
    abstract fun getAllPassesLive(): LiveData<List<DatabasePassEntity>>

    @Query("SELECT * FROM passes WHERE userId = :key ORDER BY purchase_time ASC")
    abstract fun getAllPassesFromUser(key: String): List<DatabasePassEntity>

}