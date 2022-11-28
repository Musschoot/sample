package training.squads.fitnessapp.screens.userProfile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.database.user.DatabaseDaoUser
import training.squads.fitnessapp.domain.User
import training.squads.fitnessapp.repository.UserRepository.UserRepository
import training.squads.fitnessapp.sharedPreferences

class UserProfileViewModel(val database: DatabaseDaoUser, application: Application): AndroidViewModel(application)  {

    private val db = DatabaseSquads.getInstance(application.applicationContext)
    private val repository = UserRepository(db)

    var currentUser: LiveData<User> = repository.user

    private val _userSubmitEvent = MutableLiveData<Boolean>()
    val userSubmitEvent: LiveData<Boolean>
        get() = _userSubmitEvent

    init {
        _userSubmitEvent.value = false
        viewModelScope.launch {
            // TODO: Work with API statuses
            repository.refreshUser()
        }
    }

    /**
     * Registers a click on the submit button
     */
    fun submitUserCLick() {
        _userSubmitEvent.value = true
    }

    /**
     * Checks whether the submit event has been performed
     */
    fun submitEventDone() {
        _userSubmitEvent.value = false
    }

}