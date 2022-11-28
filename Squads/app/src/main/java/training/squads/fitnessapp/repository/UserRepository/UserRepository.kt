package training.squads.fitnessapp.repository.UserRepository

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.database.seeds.UserSeeder
import training.squads.fitnessapp.database.user.asUserWithPassesDomainModel
import training.squads.fitnessapp.domain.User
import training.squads.fitnessapp.domain.asDatabaseUserListModel
import training.squads.fitnessapp.network.*
import training.squads.fitnessapp.sharedPreferences

class UserRepository(private val database: DatabaseSquads) {

    private var sharedPrefs = sharedPreferences
    val user = MediatorLiveData<User>()
    var email = sharedPrefs.emailCurrentUser!!

    private var changeableLiveUserData = Transformations.map(database.databaseDaoUser.getUserWithPassesLive(/*"fe.vanmanshoven@hotmail.com"*/sharedPrefs.emailCurrentUser!!)) {
        it.asUserWithPassesDomainModel()
    }

    init {
        user.addSource(changeableLiveUserData) {
            user.setValue(it)
        }
    }

    suspend fun refreshUser() {
        withContext(Dispatchers.IO) {
            val userProperty = getUserByEmail(email)
            val user = userProperty.asDatabaseUserWithPasses()//.asDatabaseUser()
            database.databaseDaoPass.delete(user.passes)
            database.databaseDaoPass.insert(user.passes)
            Log.i("Database status", "User has been added successfully to the database")
        }
    }

    /**
     * Method used to get a specific user
     * @param email The email address of the user, serving as an id (unique constraint)
     * @return A user instance
     */
    suspend fun getUserByEmail(email: String): UserProperty {
        val user = ApiSquads.retrofitService.getUserByEmail("fe.vanmanshoven@hotmail.com"/*email*/).await()
        return user
    }

}