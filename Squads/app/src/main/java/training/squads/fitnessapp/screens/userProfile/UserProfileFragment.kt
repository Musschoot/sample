package training.squads.fitnessapp.screens.userProfile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import training.squads.fitnessapp.R
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.database.seeds.UserSeeder
import training.squads.fitnessapp.database.user.DatabaseDaoUser
import training.squads.fitnessapp.databinding.FragmentUserProfileBinding
import training.squads.fitnessapp.domain.User
import training.squads.fitnessapp.domain.asDatabaseUserListModel
import training.squads.fitnessapp.network.SessionProperty
import training.squads.fitnessapp.network.UserProperty
import training.squads.fitnessapp.repository.UserRepository.UserRepository
import training.squads.fitnessapp.sharedPreferences

class UserProfileFragment: Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var viewModel: UserProfileViewModel
    lateinit var adapter: PassListAdapter
    private var sharedPrefs = sharedPreferences
    // private var email = sharedPrefs.emailCurrentUser!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

            // View model definition and binding
            val app = requireNotNull(this.activity).application
            val dataSource = DatabaseSquads.getInstance(app).databaseDaoUser
        
            adapter = PassListAdapter(
                PassesListener {
                    // pass -> view!!.findNavController().navigate(UserProfileFragmentDirections.action TODO: (pass)) go to detail page
                    pass -> Log.i("Pass list adapter, total sessions", pass.sessions.toString())
                }
            )

            val viewModelFactory = UserProfileViewModelFactory(dataSource, app, adapter)
            viewModel = ViewModelProvider(this, viewModelFactory)[UserProfileViewModel::class.java]
            binding.userProfileViewModel = viewModel
            binding.lifecycleOwner = this
            binding.userProfileEmail.text!!.clear()
            binding.userProfileEmail.text!!.append(sharedPrefs.emailCurrentUser)
            viewModel.userSubmitEvent.observe(viewLifecycleOwner) { userSubmitEvent ->
                if (userSubmitEvent) {
                    Toast.makeText(activity, "User opgevraagd", Toast.LENGTH_SHORT).show()
                    // viewModel.currentUser //.getUserWithEmail()
                    viewModel.submitEventDone()
                }
            }

            binding.creditsListRv.adapter = adapter

            binding.userProfileViewModel = viewModel
            binding.lifecycleOwner = this

            viewModel.currentUser.observe(viewLifecycleOwner) {
                adapter.submitList(it.passes)
            }

            return binding.root
        }
}