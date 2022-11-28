package training.squads.fitnessapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

abstract class BaseDao<T: BaseEntity> {

    /**
     * Inserts an entity in the pertaining database table
     * @param entity The entity to insert
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insert(entity: T)

    /**
     * Inserts multiple entities in the pertaining database table
     * @param entities The entities to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entities: List<T>)

    /*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg entities: T)
    */

    /**
     * Updates an entity in the pertaining database table
     * @param entity The entity to update
     */
    @Update(onConflict = OnConflictStrategy.ABORT)
    abstract fun update(entity: T)

    /**
     * Updates multiple entities in the pertaining database table
     * @param entities The entities to update
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(entities: List<T>)

    /*
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(vararg entities: T)
    */

    /**
     * Deletes an entity in the pertaining database table
     * @param entity The entity to delete
     */
    @Delete
    abstract fun delete(entity: T)

    /**
     * Deletes multiple entities in the pertaining database table
     * @param entities The entities to delete
     */
    @Delete
    abstract fun delete(entities: List<T>)

    /*
    @Delete
    abstract fun delete(vararg entities: T)
    */
}

abstract class BaseEntity {
    abstract val id: String
}