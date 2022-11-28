package training.squads.fitnessapp.screens.groupLessons

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.findNavController
import kotlinx.coroutines.launch
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.database.grouplesson.*
import training.squads.fitnessapp.domain.GroupLesson
import training.squads.fitnessapp.repository.grouplesson.GroupLessonRepository

class GroupLessonsOverviewViewModel(dataSource: DatabaseDaoGroupLesson, application: Application): AndroidViewModel(application) {

    // private val _status: MutableLiveData<List<GroupLesson>> = MutableLiveData()
    // val status: LiveData<List<GroupLesson>>
    //    get() = _status

    private val currentFilter: String? = null
    val database = DatabaseSquads.getInstance(application.applicationContext)
    private val repository = GroupLessonRepository(database)

    val groupLessons = repository.groupLessonsFilter

    init {
        Log.i("Database status", "Loading group lessons (initialization)")
        viewModelScope.launch {
            // TODO: Work with API statuses
            repository.refreshGroupLessons()
        }
    }

    fun deleteGroupLesson(groupLesson: GroupLesson) {
        viewModelScope.launch {
            deleteProjectWithRepository(groupLesson)
        }
    }

    suspend fun deleteProjectWithRepository(groupLesson: GroupLesson) {
        repository.deleteGroupLesson(groupLesson)
    }

}
