package training.squads.fitnessapp.screens.groupLessonDetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GroupLessonViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroupLessonViewModel::class.java)) {
            return GroupLessonViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}