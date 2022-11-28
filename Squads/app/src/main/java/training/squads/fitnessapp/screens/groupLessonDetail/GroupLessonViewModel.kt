package training.squads.fitnessapp.screens.groupLessonDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.domain.GroupLesson
import training.squads.fitnessapp.repository.grouplesson.GroupLessonRepository

class GroupLessonViewModel(application: Application): AndroidViewModel(application) {
    private val _currentGroupLesson = MutableLiveData<GroupLesson>()
    val currentGroupLesson: LiveData<GroupLesson>
        get() = _currentGroupLesson

    private val repository = GroupLessonRepository(DatabaseSquads.getInstance(application.applicationContext))
}