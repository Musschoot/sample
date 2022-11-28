package training.squads.fitnessapp.utilities

import android.content.Context
import android.content.SharedPreferences

/**
 * Class used to store user variables globally
 * @param context The application context
 */
class SharedPreferences(context: Context) {

    private val FIRSTNAME = "FIRSTNAME"
    private val LASTNAME = "LASTNAME"
    private val EMAIL = "EMAIL"
    private val TELEPHONE = "TELEPHONE"

    private val preferences: SharedPreferences = context.getSharedPreferences(EMAIL, Context.MODE_PRIVATE)

    var firstNameCurrentUser: String?
        get() = preferences.getString(FIRSTNAME, "")
        set(value) = preferences.edit().putString(FIRSTNAME, value).apply()

    var lastNameCurrentUser: String?
        get() = preferences.getString(LASTNAME, "")
        set(value) = preferences.edit().putString(LASTNAME, value).apply()

    var emailCurrentUser: String?
        get() = preferences.getString(EMAIL, "")
        set(value) = preferences.edit().putString(EMAIL, value).apply()

    var telephoneCurrentUser: String?
        get() = preferences.getString(TELEPHONE, "")
        set(value) = preferences.edit().putString(TELEPHONE, value).apply()

}