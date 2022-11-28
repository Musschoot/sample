package training.squads.fitnessapp.screens.groupLessons

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import training.squads.fitnessapp.R
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.databinding.FragmentGroupLessonsOverviewBinding

class GroupLessonsOverviewFragment: Fragment() {

    lateinit var binding: FragmentGroupLessonsOverviewBinding
    lateinit var viewModel: GroupLessonsOverviewViewModel
    lateinit var adapter: GroupLessonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_lessons_overview, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = DatabaseSquads.getInstance(application).databaseDaoGroupLesson

        adapter = GroupLessonListAdapter(
            GroupLessonsListener {
                    groupLesson -> view!!.findNavController().navigate(GroupLessonsOverviewFragmentDirections.actionGroupLessonsOverviewFragmentToGroupLessonFragment(groupLesson))
            },
            GroupLessonDeleteListener {
                    groupLesson  -> viewModel.deleteGroupLesson(groupLesson)
            },
            GroupLessonUpdateListener {
                groupLesson -> view!!.findNavController().navigate(GroupLessonsOverviewFragmentDirections.actionGroupLessonsOverviewFragmentToUpdateGroupLessonFragment(groupLesson))
            }
        )

        binding.groupLessonsListRv.adapter = adapter

        val viewModelFactory = GroupLessonsViewModelFactory(dataSource, application, adapter)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GroupLessonsOverviewViewModel::class.java)

        binding.groupLessonsOverviewViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.groupLessons.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}