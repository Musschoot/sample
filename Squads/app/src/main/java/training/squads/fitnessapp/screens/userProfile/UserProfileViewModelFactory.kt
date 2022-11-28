package training.squads.fitnessapp.screens.userProfile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import training.squads.fitnessapp.database.grouplesson.DatabaseDaoGroupLesson
import training.squads.fitnessapp.database.user.DatabaseDaoUser
import training.squads.fitnessapp.screens.groupLessons.GroupLessonListAdapter
import java.lang.IllegalArgumentException

class UserProfileViewModelFactory(private val dataSource: DatabaseDaoUser, private val application: Application, private val adapter: PassListAdapter): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}