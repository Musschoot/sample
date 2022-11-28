package training.squads.fitnessapp.screens.addGroupLesson

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.database.grouplesson.DatabaseDaoGroupLesson
import training.squads.fitnessapp.domain.GroupLesson
import training.squads.fitnessapp.domain.GroupLessonType
import training.squads.fitnessapp.repository.grouplesson.GroupLessonRepository
import java.time.LocalDateTime
import java.util.*

class AddGroupLessonViewModel(val database: DatabaseDaoGroupLesson, application: Application): AndroidViewModel(application) {

    private val _groupLessonSubmitEvent = MutableLiveData<Boolean>()
    val groupLessonSubmitEvent: LiveData<Boolean>
        get() = _groupLessonSubmitEvent

    private val db = DatabaseSquads.getInstance(application.applicationContext)
    private val repository = GroupLessonRepository(db)

    init {
        _groupLessonSubmitEvent.value = false
    }

    /**
     * Registers a click on the submit button
     */
    fun submitGroupLessonCLick() {
        _groupLessonSubmitEvent.value = true
    }

    /**
     * Checks whether the submit event has been performed
     */
    fun submitEventDone() {
        _groupLessonSubmitEvent.value = false
    }

    /**
     * Method used to add a group lesson
     * @param title The name of a group lesson
     * @param type The type of a group lesson
     * @param description A short explanation of a group lesson
     * @param startDateTime The start of a group lesson
     * @param endDateTime The end of a group lesson
     * @param totalParticipants The number of allowed participants to a group lesson
     */
    fun submitGroupLesson(
        title: String,
        type: GroupLessonType,
        description: String,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        totalParticipants: Int
    ) {
        viewModelScope.launch {
            val groupLesson = GroupLesson(
                id = UUID.randomUUID(),
                title = title,
                type = type,
                description = description,
                startDateTime = startDateTime,
                endDateTime = endDateTime,
                totalParticipants = totalParticipants
            )
            saveGroupLessonWithRepository(groupLesson)
        }
    }

    /**
     * Method used to save a group lesson by means of the repository
     * @param newGroupLesson A group lesson instance
     */
    suspend fun saveGroupLessonWithRepository(newGroupLesson: GroupLesson) {
        repository.addGroupLesson(newGroupLesson)
    }

}