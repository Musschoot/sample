package training.squads.fitnessapp.screens.updateGroupLesson

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import training.squads.fitnessapp.database.grouplesson.DatabaseDaoGroupLesson
import java.lang.IllegalArgumentException

class UpdateGroupLessonViewModelFactory(private val dataSource: DatabaseDaoGroupLesson, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateGroupLessonViewModel::class.java)) {
            return UpdateGroupLessonViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}