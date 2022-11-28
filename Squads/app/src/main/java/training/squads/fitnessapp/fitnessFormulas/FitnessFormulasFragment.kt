package training.squads.fitnessapp.fitnessFormulas

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.auth0.android.Auth0
import training.squads.fitnessapp.R

/**
 * A simple [Fragment] subclass
 * Displays the login functionality
 * @property account The Auth0 account information
 * @property trialText The text mentioning that you are logged in
 * @property loggedIn The text stating whether you are logged in or not
 */
class FitnessFormulasFragment: Fragment() {

    private lateinit var account : Auth0
    private var loggedIn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_fitness_formulas, container, false)

        setHasOptionsMenu(true)

        account = Auth0(
            getString(R.string.auth0_client_id),
            getString(R.string.auth0_domain)
        )

        return view
    }

}