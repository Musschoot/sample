package training.squads.fitnessapp.security

// import androidx.navigation.findNavController
// import androidx.navigation.ui.NavigationUI
import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import timber.log.Timber
import training.squads.fitnessapp.MainActivity
import training.squads.fitnessapp.R
import training.squads.fitnessapp.sharedPreferences

/**
 * A simple [Fragment] subclass
 * Displays the login functionality
 * @property account The Auth0 account information
 * @property loggedInText The text mentioning that you are logged in
 * @property welcomeText The text set to welcome a user
 * @property loggedIn The text stating whether you are logged in or not
 */
class LoginFragment: Fragment() {

    private lateinit var account : Auth0
    private lateinit var loggedInText: TextView
    private lateinit var welcomeText: TextView
    private var sharedPrefs = sharedPreferences
    private var loggedIn = false
    private var email: String? = null
    private var username: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var telephone: String? = null
    private val httpClient = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        setHasOptionsMenu(true)

        account = Auth0(
            getString(R.string.auth0_client_id),
            getString(R.string.auth0_domain)
        )

        val button = view.findViewById<Button>(R.id.login_button)
        button?.setOnClickListener {
            loginWithBrowser()
        }

        val adminButton = view.findViewById<Button>(R.id.admin_button)
        adminButton?.setOnClickListener {
            noLogin()
        }

        val logoutButton = view.findViewById<Button>(R.id.logout_button)
        logoutButton?.setOnClickListener {
            logout()
        }

        welcomeText = view.findViewById(R.id.welcome_text)
        loggedInText = view.findViewById(R.id.login_text)

        checkIfToken()
        setLoggedInText()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    /**
     * Checks if an access token is present
     */
    private fun checkIfToken(){
        val token = CredentialsManager.getAccessToken(requireContext())
        if(token != null){
            showUserProfile(token)
        }
        else {
            Toast.makeText(context, getText(R.string.invalid_login), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Sets the text when you are logged in
     */
    private fun setLoggedInText(firstName:String = "") {
        if(loggedIn) {
            var welcomeMessage = StringBuilder()
            if(!firstName.isNullOrBlank()) {
                welcomeText.text = welcomeMessage.append(getString(R.string.welcome_text)).append(" " + firstName)
            } else {
                welcomeText.text = getText(R.string.welcome_text)
            }
            loggedInText.text = getText(R.string.login_text)
        }
        else {
            welcomeText.text = getText(R.string.welcome_text)
            loggedInText.text = getText(R.string.logout_text)
        }
    }

    /**
     * Method used for login, stating success or failure
     */
    private fun loginWithBrowser() {

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(requireContext(), object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    loggedIn = false
                    Toast.makeText(context, getText(R.string.login_nok), Toast.LENGTH_SHORT).show()
                }
                override fun onSuccess(result: Credentials) {

                    // val accessToken = result.accessToken
                    // Toast.makeText(context, accessToken, Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, getText(R.string.login_ok), Toast.LENGTH_SHORT).show()

                    CredentialsManager.saveCredentials(requireContext(), result)
                    checkIfToken()

                    // Check if email is returned
                    showUserProfile(result.accessToken)
                    Log.i("AUTH ZERO", email.toString());
                    Log.i("AUTH ZERO", username.toString());
                    Log.i("AUTH ZERO", firstName.toString());
                    Log.i("AUTH ZERO", "voornaam opgehaald");
                    // Go to next fragment
                    val intent = Intent (activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    activity?.startActivity(intent)
                }
            })
    }

    /**
     * Method used to logout of the app
     */
    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(requireContext(), object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    Toast.makeText(context, getText(R.string.logout_ok), Toast.LENGTH_SHORT).show()
                    loggedIn = false
                    setLoggedInText("")
                }

                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(context, getText(R.string.logout_nok), Toast.LENGTH_SHORT).show()
                }
            })
    }

    /**
     * Method used to display the user profile
     *
     * @property accessToken The access token of the user
     */
    private fun showUserProfile(accessToken: String){
        val client = AuthenticationAPIClient(account)

        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Timber.i(error.stackTraceToString())
                    loggedIn = false
                    setLoggedInText()
                }

                override fun onSuccess(result: UserProfile) {
                    Timber.i("SUCCESS! got the user profile")
                    loggedIn = true
                    email = result.email
                    sharedPrefs.emailCurrentUser = email
                    username = result.name

                    val userRequest = Request.Builder()
                        .url("https://squadstraining.eu.auth0.com/api/v2/users/" + result.getId())
                        .header("content-type", "application/json")
                        .header("authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlEzeVNyZlVUdDhuaFhsaHJLSXlRTiJ9.eyJpc3MiOiJodHRwczovL3NxdWFkc3RyYWluaW5nLmV1LmF1dGgwLmNvbS8iLCJzdWIiOiJPbWVDaDhjeHdMa214dDQ1U2tzVlB3dEkwQjR0NXlubUBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9zcXVhZHN0cmFpbmluZy5ldS5hdXRoMC5jb20vYXBpL3YyLyIsImlhdCI6MTY2OTA1MTk1NiwiZXhwIjoxNjcxNjQzOTU2LCJhenAiOiJPbWVDaDhjeHdMa214dDQ1U2tzVlB3dEkwQjR0NXlubSIsInNjb3BlIjoicmVhZDpjbGllbnRfZ3JhbnRzIGNyZWF0ZTpjbGllbnRfZ3JhbnRzIGRlbGV0ZTpjbGllbnRfZ3JhbnRzIHVwZGF0ZTpjbGllbnRfZ3JhbnRzIHJlYWQ6dXNlcnMgdXBkYXRlOnVzZXJzIGRlbGV0ZTp1c2VycyBjcmVhdGU6dXNlcnMgcmVhZDp1c2Vyc19hcHBfbWV0YWRhdGEgdXBkYXRlOnVzZXJzX2FwcF9tZXRhZGF0YSBkZWxldGU6dXNlcnNfYXBwX21ldGFkYXRhIGNyZWF0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgcmVhZDp1c2VyX2N1c3RvbV9ibG9ja3MgY3JlYXRlOnVzZXJfY3VzdG9tX2Jsb2NrcyBkZWxldGU6dXNlcl9jdXN0b21fYmxvY2tzIGNyZWF0ZTp1c2VyX3RpY2tldHMgcmVhZDpjbGllbnRzIHVwZGF0ZTpjbGllbnRzIGRlbGV0ZTpjbGllbnRzIGNyZWF0ZTpjbGllbnRzIHJlYWQ6Y2xpZW50X2tleXMgdXBkYXRlOmNsaWVudF9rZXlzIGRlbGV0ZTpjbGllbnRfa2V5cyBjcmVhdGU6Y2xpZW50X2tleXMgcmVhZDpjb25uZWN0aW9ucyB1cGRhdGU6Y29ubmVjdGlvbnMgZGVsZXRlOmNvbm5lY3Rpb25zIGNyZWF0ZTpjb25uZWN0aW9ucyByZWFkOnJlc291cmNlX3NlcnZlcnMgdXBkYXRlOnJlc291cmNlX3NlcnZlcnMgZGVsZXRlOnJlc291cmNlX3NlcnZlcnMgY3JlYXRlOnJlc291cmNlX3NlcnZlcnMgcmVhZDpkZXZpY2VfY3JlZGVudGlhbHMgdXBkYXRlOmRldmljZV9jcmVkZW50aWFscyBkZWxldGU6ZGV2aWNlX2NyZWRlbnRpYWxzIGNyZWF0ZTpkZXZpY2VfY3JlZGVudGlhbHMgcmVhZDpydWxlcyB1cGRhdGU6cnVsZXMgZGVsZXRlOnJ1bGVzIGNyZWF0ZTpydWxlcyByZWFkOnJ1bGVzX2NvbmZpZ3MgdXBkYXRlOnJ1bGVzX2NvbmZpZ3MgZGVsZXRlOnJ1bGVzX2NvbmZpZ3MgcmVhZDpob29rcyB1cGRhdGU6aG9va3MgZGVsZXRlOmhvb2tzIGNyZWF0ZTpob29rcyByZWFkOmFjdGlvbnMgdXBkYXRlOmFjdGlvbnMgZGVsZXRlOmFjdGlvbnMgY3JlYXRlOmFjdGlvbnMgcmVhZDplbWFpbF9wcm92aWRlciB1cGRhdGU6ZW1haWxfcHJvdmlkZXIgZGVsZXRlOmVtYWlsX3Byb3ZpZGVyIGNyZWF0ZTplbWFpbF9wcm92aWRlciBibGFja2xpc3Q6dG9rZW5zIHJlYWQ6c3RhdHMgcmVhZDppbnNpZ2h0cyByZWFkOnRlbmFudF9zZXR0aW5ncyB1cGRhdGU6dGVuYW50X3NldHRpbmdzIHJlYWQ6bG9ncyByZWFkOmxvZ3NfdXNlcnMgcmVhZDpzaGllbGRzIGNyZWF0ZTpzaGllbGRzIHVwZGF0ZTpzaGllbGRzIGRlbGV0ZTpzaGllbGRzIHJlYWQ6YW5vbWFseV9ibG9ja3MgZGVsZXRlOmFub21hbHlfYmxvY2tzIHVwZGF0ZTp0cmlnZ2VycyByZWFkOnRyaWdnZXJzIHJlYWQ6Z3JhbnRzIGRlbGV0ZTpncmFudHMgcmVhZDpndWFyZGlhbl9mYWN0b3JzIHVwZGF0ZTpndWFyZGlhbl9mYWN0b3JzIHJlYWQ6Z3VhcmRpYW5fZW5yb2xsbWVudHMgZGVsZXRlOmd1YXJkaWFuX2Vucm9sbG1lbnRzIGNyZWF0ZTpndWFyZGlhbl9lbnJvbGxtZW50X3RpY2tldHMgcmVhZDp1c2VyX2lkcF90b2tlbnMgY3JlYXRlOnBhc3N3b3Jkc19jaGVja2luZ19qb2IgZGVsZXRlOnBhc3N3b3Jkc19jaGVja2luZ19qb2IgcmVhZDpjdXN0b21fZG9tYWlucyBkZWxldGU6Y3VzdG9tX2RvbWFpbnMgY3JlYXRlOmN1c3RvbV9kb21haW5zIHVwZGF0ZTpjdXN0b21fZG9tYWlucyByZWFkOmVtYWlsX3RlbXBsYXRlcyBjcmVhdGU6ZW1haWxfdGVtcGxhdGVzIHVwZGF0ZTplbWFpbF90ZW1wbGF0ZXMgcmVhZDptZmFfcG9saWNpZXMgdXBkYXRlOm1mYV9wb2xpY2llcyByZWFkOnJvbGVzIGNyZWF0ZTpyb2xlcyBkZWxldGU6cm9sZXMgdXBkYXRlOnJvbGVzIHJlYWQ6cHJvbXB0cyB1cGRhdGU6cHJvbXB0cyByZWFkOmJyYW5kaW5nIHVwZGF0ZTpicmFuZGluZyBkZWxldGU6YnJhbmRpbmcgcmVhZDpsb2dfc3RyZWFtcyBjcmVhdGU6bG9nX3N0cmVhbXMgZGVsZXRlOmxvZ19zdHJlYW1zIHVwZGF0ZTpsb2dfc3RyZWFtcyBjcmVhdGU6c2lnbmluZ19rZXlzIHJlYWQ6c2lnbmluZ19rZXlzIHVwZGF0ZTpzaWduaW5nX2tleXMgcmVhZDpsaW1pdHMgdXBkYXRlOmxpbWl0cyBjcmVhdGU6cm9sZV9tZW1iZXJzIHJlYWQ6cm9sZV9tZW1iZXJzIGRlbGV0ZTpyb2xlX21lbWJlcnMgcmVhZDplbnRpdGxlbWVudHMgcmVhZDphdHRhY2tfcHJvdGVjdGlvbiB1cGRhdGU6YXR0YWNrX3Byb3RlY3Rpb24gcmVhZDpvcmdhbml6YXRpb25zIHVwZGF0ZTpvcmdhbml6YXRpb25zIGNyZWF0ZTpvcmdhbml6YXRpb25zIGRlbGV0ZTpvcmdhbml6YXRpb25zIGNyZWF0ZTpvcmdhbml6YXRpb25fbWVtYmVycyByZWFkOm9yZ2FuaXphdGlvbl9tZW1iZXJzIGRlbGV0ZTpvcmdhbml6YXRpb25fbWVtYmVycyBjcmVhdGU6b3JnYW5pemF0aW9uX2Nvbm5lY3Rpb25zIHJlYWQ6b3JnYW5pemF0aW9uX2Nvbm5lY3Rpb25zIHVwZGF0ZTpvcmdhbml6YXRpb25fY29ubmVjdGlvbnMgZGVsZXRlOm9yZ2FuaXphdGlvbl9jb25uZWN0aW9ucyBjcmVhdGU6b3JnYW5pemF0aW9uX21lbWJlcl9yb2xlcyByZWFkOm9yZ2FuaXphdGlvbl9tZW1iZXJfcm9sZXMgZGVsZXRlOm9yZ2FuaXphdGlvbl9tZW1iZXJfcm9sZXMgY3JlYXRlOm9yZ2FuaXphdGlvbl9pbnZpdGF0aW9ucyByZWFkOm9yZ2FuaXphdGlvbl9pbnZpdGF0aW9ucyBkZWxldGU6b3JnYW5pemF0aW9uX2ludml0YXRpb25zIHJlYWQ6b3JnYW5pemF0aW9uc19zdW1tYXJ5IGNyZWF0ZTphY3Rpb25zX2xvZ19zZXNzaW9ucyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.sv1pEPiLz-_wT8O-IpUFYlg8E_Dlxqx2Ceo_-st70yVMa08vI-wStMXt83AdpU88cPEh2W__bQvMGXxXyxwm2dlYaCZlql5hOVx-ctwBktG5_weP1Qfhx5EuYZSNFgiHs_qDDJveIDm4hfiEApLwHXN3sVGosHTf0jHiXNplaEyFwblea1kSkGVaj3S4BwF5FyqDkcwlmJDhzMUBUcImgg_whtH19vsJrDhGhD_edjo7mEvxk04Bh-JF4xYH_hji1k7XyLOUluC9Tdzr1i-JhiTIMnwE8ZsH4CCdpQki5mRFpIuzwQbkShGUKQuubD0QF2K94bK5Hr0GB40FeIBzgQ")
                        .build();

                    httpClient.newCall(userRequest).execute().use { response ->
                        if (!response.isSuccessful) throw RuntimeException("status code $response");

                        var test = response.body!!.string()
                        val responseObject = JSONObject("""
                            ${`test`}
                        """.trimIndent())
                        var userMetadata = responseObject.getJSONObject("user_metadata")

                        firstName = userMetadata.getString("first_name")
                        sharedPrefs.firstNameCurrentUser = firstName
                        lastName = userMetadata.getString("last_name")
                        sharedPrefs.lastNameCurrentUser = lastName
                        telephone = userMetadata.getString("telephone")
                        sharedPrefs.telephoneCurrentUser = telephone
                    }
                    setLoggedInText(firstName!!)
                }
            })

    }

    private fun noLogin() {
        val intent = Intent (activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity?.startActivity(intent)
    }

}