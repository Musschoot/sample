package training.squads.fitnessapp.screens.addGroupLesson

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import training.squads.fitnessapp.R
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.databinding.FragmentAddGroupLessonBinding
import training.squads.fitnessapp.utilities.GroupLessonTypeConverter
import training.squads.fitnessapp.utilities.MaterialDateConverter
import java.time.LocalDateTime

class AddGroupLessonFragment: Fragment() {

    private lateinit var binding: FragmentAddGroupLessonBinding
    private lateinit var viewModel: AddGroupLessonViewModel
    private var mdateConverter = MaterialDateConverter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Definition of layout inflation
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_group_lesson, container, false)

        // Property instantiation
        // TODO: Add days of week picker, to be able create recurrent sessions later
        var type = "Training"

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
        val datePickerStartDate =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build()
        var startDate = LocalDateTime.of(1, 1, 1, 0, 0, 0)
        /*
        var datePickerEndDate =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.select_date)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build()
         */

        val timePickerStartTime =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(15)
                .setTitleText(R.string.select_time)
                .build()
        val timePickerEndTime =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(15)
                .setTitleText(R.string.select_time)
                .build()

        // Binding of radio button values
        val lessonTypeRadioButtonTraining: RadioButton = binding.groupLessonTypeRadioButton1
        val lessonTypeRadioButtonYoga: RadioButton = binding.groupLessonTypeRadioButton2

        lessonTypeRadioButtonTraining.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                type = buttonView.text.toString()
            }
        }
        lessonTypeRadioButtonYoga.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                type = buttonView.text.toString()
            }
        }

        // Binding of date values
        binding.groupLessonStartDateCalendar.setOnClickListener {
            datePickerStartDate.show(parentFragmentManager, "groupLessonStartDateCalendar")
        }
        /*
        binding.groupLessonEndDateCalendar.setOnClickListener {
            datePickerEndDate.show(parentFragmentManager, "groupLessonEndDateCalendar")
        }
        */
        datePickerStartDate.addOnPositiveButtonClickListener {
            binding.groupLessonStartDate.setText(datePickerStartDate.headerText)
            startDate = mdateConverter.dateLongToLocalDateTimeConversion(datePickerStartDate.selection)
        }
        /*
        datePickerEndDate.addOnPositiveButtonClickListener {
            binding.groupLessonEndDate.setText(datePickerEndDate.headerText)
        }
        */

        // Binding of time values
        binding.groupLessonStartTimeCalendar.setOnClickListener {
            timePickerStartTime.show(parentFragmentManager,"groupLessonStartTimeCalendar")
        }
        binding.groupLessonEndTimeCalendar.setOnClickListener {
            timePickerEndTime.show(parentFragmentManager,"groupLessonStartTimeCalendar")
        }
        timePickerStartTime.addOnPositiveButtonClickListener {
            binding.groupLessonStartTime.setText(timePickerStartTime.hour.toString() + ":" + timePickerStartTime.minute.toString())
        }
        timePickerEndTime.addOnPositiveButtonClickListener {
            binding.groupLessonEndTime.setText(timePickerEndTime.hour.toString() + ":" + timePickerEndTime.minute.toString())
        }

        // Definition of view model and binding
        val app = requireNotNull(this.activity).application
        val dataSource = DatabaseSquads.getInstance(app).databaseDaoGroupLesson
        val viewModelFactory = AddGroupLessonViewModelFactory(dataSource, app)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddGroupLessonViewModel::class.java]
        binding.addGroupLessonViewModel = viewModel
        binding.lifecycleOwner = this

        // Binding of other group lesson properties, besides the type and date related ones
        val title: Editable? = binding.groupLessonName.text
        val description: Editable? = binding.groupLessonDescription.text
        val participants: Editable? = binding.groupLessonParticipants.text

        // TODO: Validation
        viewModel.groupLessonSubmitEvent.observe(viewLifecycleOwner) { groupLessonSubmitEvent ->
            if (groupLessonSubmitEvent) {
                Toast.makeText(activity, R.string.group_lesson_created, Toast.LENGTH_SHORT).show()

                viewModel.submitGroupLesson(
                    title.toString(),
                    GroupLessonTypeConverter().toGroupLessonType(type.uppercase()),
                    description.toString(),
                    startDate
                        .plusHours(timePickerStartTime.hour.toLong())
                        .plusMinutes(timePickerStartTime.minute.toLong()),
                    // MaterialDateConverter().toDate(binding.groupLessonEndDate.text.toString()).plusHours(timePickerEndTime.hour.toLong()).plusMinutes(timePickerEndTime.minute.toLong()),
                    startDate
                        .plusHours(timePickerEndTime.hour.toLong())
                        .plusMinutes(timePickerEndTime.minute.toLong()),
                    Integer.parseInt(participants.toString())
                )

                Toast.makeText(activity, R.string.group_lesson_saved, Toast.LENGTH_SHORT).show()
                view?.findNavController()
                    ?.navigate(AddGroupLessonFragmentDirections.actionAddGroupLessonFragmentToGroupLessonsOverviewFragment())
                viewModel.submitEventDone()
            }
        }

        return binding.root
    }
}