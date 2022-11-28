package training.squads.fitnessapp.database.grouplesson

import androidx.lifecycle.LiveData
import androidx.room.*
import training.squads.fitnessapp.database.BaseDao

/**
 * This abstract class contains database functions for group lesson
 */
@Dao
abstract class DatabaseDaoGroupLesson: BaseDao<DatabaseGroupLessonEntity>() {

    /**
     * Fetches all group lessons
     * @return A list of database group lessons
     */
    @Query("SELECT * FROM group_lesson ORDER BY name ASC")
    abstract fun getAllGroupLessons(): List<DatabaseGroupLessonEntity>

    /**
     * Fetches all group lessons, taking state into account
     * @return A list of LiveData database group lessons
     */
    @Query("SELECT * FROM group_lesson ORDER BY name ASC")
    abstract fun getAllGroupLessonsLive(): LiveData<List<DatabaseGroupLessonEntity>>

    /**
     * Fetches a group lesson by means of its id
     * @property key The group lesson id
     * @return A database group lesson
     */
    @Query("SELECT * FROM group_lesson WHERE lesson_id = :key")
    abstract fun getGroupLesson(key: String): DatabaseGroupLessonEntity

    /**
     * Deletes all database group lessons
     */
    @Query("DELETE FROM group_lesson")
    abstract fun clear()

    /* TODO: Finalize relations
    /**
     * Fetches all group lessons from the database, including exercise objects
     */
    @Transaction
    @Query("SELECT * FROM group_lesson")
    abstract fun getGroupLessonsWithExercises(): List<DatabaseGroupLessonWithExerciseEntity>

    /**
     * Fetches all group lessons from the database, including comment objects
     */
    @Transaction
    @Query("SELECT * FROM group_lesson")
    abstract fun getGroupLessonsWithComments(): List<DatabaseGroupLessonWithCommentEntity>
    */
}