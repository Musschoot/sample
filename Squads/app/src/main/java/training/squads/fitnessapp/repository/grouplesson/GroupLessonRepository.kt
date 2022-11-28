package training.squads.fitnessapp.repository.grouplesson

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.database.grouplesson.asDomainModel
import training.squads.fitnessapp.database.seeds.GroupLessonSeeder
import training.squads.fitnessapp.domain.GroupLesson
import training.squads.fitnessapp.domain.asDatabaseGroupLessonModel
import training.squads.fitnessapp.network.ApiSquads
import training.squads.fitnessapp.network.SessionProperty
import training.squads.fitnessapp.utilities.uuidConverter

/**
 * The group lesson repository
 * @param database A group lesson database instance
 */
class GroupLessonRepository(private val database: DatabaseSquads) {

    val idConverter = uuidConverter()
    val gls = GroupLessonSeeder.getInstance()
    val groupLessons = MediatorLiveData<List<GroupLesson>>()
    val filter = MutableLiveData<String>(null)
    val groupLessonsFilter = Transformations.switchMap(filter) { filter ->
        when (filter) {
            "all" -> Transformations.map(database.databaseDaoGroupLesson.getAllGroupLessonsLive()) {
                it.asDomainModel()
            }
            else -> Transformations.map(database.databaseDaoGroupLesson.getAllGroupLessonsLive()) {
                it.asDomainModel()
            }
        }
    }

    private var changeableLiveGroupLessonData = Transformations.map(database.databaseDaoGroupLesson.getAllGroupLessonsLive()) {
        it.asDomainModel()
    }

    init {
        groupLessons.addSource(changeableLiveGroupLessonData) {
            groupLessons.setValue(it)
        }
    }

    /**
     * Method used to refresh the list of group lessons
     */
    suspend fun refreshGroupLessons() {
        withContext(Dispatchers.IO) {
            // Use seeder only for development purposes
            if(gls.status) {
                database.databaseDaoGroupLesson.clear()
                Log.i("Database status", "Group lessons have all been successfully deleted from the database")
                // val groupLessons = gls.seedGroupLessons()
                // database.databaseDaoGroupLesson.insert(groupLessons.asDatabaseGroupLessonListModel())
                // Log.i("Database status", "Group lessons have been added successfully to the database")
                gls.status = false
            }

            val sessions = ApiSquads.retrofitService.getAllSessionsAsync().await()
            val groupLesson = sessions.map { x -> x.asDatabaseGroupLesson() }
            database.databaseDaoGroupLesson.clear()
            database.databaseDaoGroupLesson.insert(groupLesson)
            Log.i("Database status", "Group lessons have been added successfully to the database")
        }
    }

    /**
     * Method used to get a specific group lesson
     * @param id The id of the group lesson to be fetched
     * @return A group lesson instance
     */
    suspend fun getGroupLessonById(id: String): SessionProperty {
        return ApiSquads.retrofitService.getSessionById(id).await()
    }

    /**
     * Method used to add a group lesson
     * @param newGroupLesson A group lesson instance
     * @return A group lesson instance
     */
    suspend fun addGroupLesson(groupLesson: GroupLesson): SessionProperty {
        val newSessionProperty = SessionProperty(
            id = groupLesson.id,
            title = groupLesson.title,
            description = groupLesson.description,
            sessionType = groupLesson.type,
            startDateTime = groupLesson.startDateTime,
            endDateTime = groupLesson.endDateTime,
            users = emptyList()
        )

        withContext(Dispatchers.IO) {
            ApiSquads.retrofitService.postSession(newSessionProperty).await()
            Log.i("API status", "Group lesson added successfully to the API")
        }
        refreshGroupLessons()

        return newSessionProperty
    }

    /**
     * Method used to update a specific group lesson from the database
     * @param groupLesson The group lesson to be updated
     */
    suspend fun updateGroupLesson(groupLesson: GroupLesson): SessionProperty {
        val newSessionProperty = SessionProperty(
            id = groupLesson.id,
            title = groupLesson.title,
            description = groupLesson.description,
            sessionType = groupLesson.type,
            startDateTime = groupLesson.startDateTime,
            endDateTime = groupLesson.endDateTime,
            users = emptyList()
        )

        withContext(Dispatchers.IO) {
            ApiSquads.retrofitService.putSession(newSessionProperty).await()
            Log.i("API status", "Group lesson adjusted successfully for the API")
        }
        Thread.sleep(250)
        refreshGroupLessons()

        return newSessionProperty
    }

    /**
     * Method used to delete a specific group lesson
     * @param groupLesson The group lesson to be deleted
     */
    suspend fun deleteGroupLesson(groupLesson: GroupLesson) {
        withContext(Dispatchers.IO) {
            ApiSquads.retrofitService.deleteSession(idConverter.fromUuid(groupLesson.id))
            database.databaseDaoGroupLesson.delete(groupLesson.asDatabaseGroupLessonModel())
            Log.i("Database status", "Group lesson has been successfully deleted")
        }
        Thread.sleep(250)
        refreshGroupLessons()
    }

    /**
     * Method used to delete all group lessons
     */
    suspend fun deleteGroupLessons() {
        withContext(Dispatchers.IO) {
            database.databaseDaoGroupLesson.clear()
            Log.i("Database status", "Group lessons have been all successfully deleted")
        }
        refreshGroupLessons()
    }

}