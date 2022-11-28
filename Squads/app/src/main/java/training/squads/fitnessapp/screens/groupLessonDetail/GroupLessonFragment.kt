package training.squads.fitnessapp.screens.groupLessonDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import training.squads.fitnessapp.R
import training.squads.fitnessapp.databinding.FragmentGroupLessonBinding

class GroupLessonFragment: Fragment() {

    private val args: GroupLessonFragmentArgs by navArgs()

    private lateinit var binding: FragmentGroupLessonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_lesson, container, false)

        val app = requireNotNull(this.activity).application

        val viewModelFactory = GroupLessonViewModelFactory(app)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(GroupLessonViewModel::class.java)

        binding.lifecycleOwner = this

        return binding.root
    }
}