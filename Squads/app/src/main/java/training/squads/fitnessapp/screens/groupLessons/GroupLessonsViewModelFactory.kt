package training.squads.fitnessapp.screens.groupLessons

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import training.squads.fitnessapp.database.grouplesson.DatabaseDaoGroupLesson

class GroupLessonsViewModelFactory(private val dataSource: DatabaseDaoGroupLesson, private val application: Application, private val adapter: GroupLessonListAdapter): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroupLessonsOverviewViewModel::class.java)) {
            return GroupLessonsOverviewViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}