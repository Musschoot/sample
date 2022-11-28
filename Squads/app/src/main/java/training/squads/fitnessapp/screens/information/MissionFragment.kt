package training.squads.fitnessapp.screens.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import timber.log.Timber
import training.squads.fitnessapp.R

/**
 * A [Fragment] instance
 * Used to display the mission statement of fitness center Squads
 */
class MissionFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("Mission fragment is created")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val missionFragment: View = inflater.inflate(R.layout.fragment_mission, container, false)

        return missionFragment
    }
}