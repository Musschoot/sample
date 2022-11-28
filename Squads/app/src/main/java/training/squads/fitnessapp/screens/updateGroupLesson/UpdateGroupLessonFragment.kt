package training.squads.fitnessapp.screens.updateGroupLesson

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import training.squads.fitnessapp.R
import training.squads.fitnessapp.database.DatabaseSquads
import training.squads.fitnessapp.database.grouplesson.dateConverter
import training.squads.fitnessapp.databinding.FragmentUpdateGroupLessonBinding
import training.squads.fitnessapp.screens.groupLessonDetail.GroupLessonFragmentArgs
import training.squads.fitnessapp.utilities.GroupLessonTypeConverter
import training.squads.fitnessapp.utilities.MaterialDateConverter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


class UpdateGroupLessonFragment: Fragment() {

    private val args: GroupLessonFragmentArgs by navArgs()
    private lateinit var binding: FragmentUpdateGroupLessonBinding
    private lateinit var viewModel: UpdateGroupLessonViewModel
    private var gtConverter: GroupLessonTypeConverter = GroupLessonTypeConverter()
    private var mdateConverter = MaterialDateConverter()
    private var dFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    private var tFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_group_lesson, container, false)

        // Listener for cancel button
        binding.cancelButtonUpdate.setOnClickListener {
            view!!.findNavController().navigate(UpdateGroupLessonFragmentDirections.actionUpdateGroupLessonFragmentToGroupLessonsOverviewFragment())
        }

        // Definition of view model and binding
        val app = requireNotNull(this.activity).application
        val dataSource = DatabaseSquads.getInstance(app).databaseDaoGroupLesson
        val viewModelFactory = UpdateGroupLessonViewModelFactory(dataSource, app)
        viewModel = ViewModelProvider(this, viewModelFactory)[UpdateGroupLessonViewModel::class.java]
        binding.updateGroupLessonViewModel = viewModel
        binding.lifecycleOwner = this

        // Set properties to current value via binding
        binding.groupLessonNameUpdate.setText(args.groupLesson.title)
        if(gtConverter.fromGroupLessonType(args.groupLesson.type).equals(getString(R.string.group_lesson_type_training), ignoreCase = true)) {
            binding.groupLessonTypeRadioGroupUpdate.check(binding.groupLessonTypeRadioButton1Update.id)
        } else {
            binding.groupLessonTypeRadioGroupUpdate.check(binding.groupLessonTypeRadioButton2Update.id)
        }
        binding.groupLessonStartDateUpdate.setText(args.groupLesson.startDateTime.format(dFormatter))
        binding.groupLessonStartTimeUpdate.setText(args.groupLesson.startDateTime.format(tFormatter))
        binding.groupLessonEndTimeUpdate.setText(args.groupLesson.endDateTime.format(tFormatter))
        binding.groupLessonDescriptionUpdate.setText(args.groupLesson.description)
        binding.groupLessonParticipantsUpdate.setText(args.groupLesson.totalParticipants.toString())

        // Property instantiation
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
        var startDate = mdateConverter.dateLongToLocalDateTimeConversion(datePickerStartDate.selection)
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
        val lessonTypeRadioButtonTraining: RadioButton = binding.groupLessonTypeRadioButton1Update
        val lessonTypeRadioButtonYoga: RadioButton = binding.groupLessonTypeRadioButton2Update

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
        binding.groupLessonStartDateCalendarUpdate.setOnClickListener {
            datePickerStartDate.show(parentFragmentManager, "groupLessonStartDateCalendar")
        }
        /*
        binding.groupLessonEndDateCalendar.setOnClickListener {
            datePickerEndDate.show(parentFragmentManager, "groupLessonEndDateCalendar")
        }
        */
        datePickerStartDate.addOnPositiveButtonClickListener {
            binding.groupLessonStartDateUpdate.setText(datePickerStartDate.headerText)
            Log.i("Logged start date", datePickerStartDate.selection.toString())
            Log.i("Logged start date", mdateConverter.dateLongToLocalDateTimeConversion(datePickerStartDate.selection).toString())
            startDate = mdateConverter.dateLongToLocalDateTimeConversion(datePickerStartDate.selection)
        }
        /*
        datePickerEndDate.addOnPositiveButtonClickListener {
            binding.groupLessonEndDate.setText(datePickerEndDate.headerText)
        }
        */

        // Binding of time values
        var startTimeChanged = false;
        var endTimeChanged = false;
        binding.groupLessonStartTimeCalendarUpdate.setOnClickListener {
            timePickerStartTime.show(parentFragmentManager,"groupLessonStartTimeCalendar")
        }
        binding.groupLessonEndTimeCalendarUpdate.setOnClickListener {
            timePickerEndTime.show(parentFragmentManager,"groupLessonStartTimeCalendar")
        }
        timePickerStartTime.addOnPositiveButtonClickListener {
            binding.groupLessonStartTimeUpdate.setText(timePickerStartTime.hour.toString() + ":" + timePickerStartTime.minute.toString())
            startTimeChanged = true
        }
        timePickerEndTime.addOnPositiveButtonClickListener {
            binding.groupLessonEndTimeUpdate.setText(timePickerEndTime.hour.toString() + ":" + timePickerEndTime.minute.toString())
            endTimeChanged = true
        }

        // Binding of other group lesson properties, besides the type and date related ones
        val title: Editable? = binding.groupLessonNameUpdate.text
        val description: Editable? = binding.groupLessonDescriptionUpdate.text
        val participants: Editable? = binding.groupLessonParticipantsUpdate.text

        // TODO: Validation
        viewModel.groupLessonSubmitEvent.observe(viewLifecycleOwner) { groupLessonSubmitEvent ->
            if (groupLessonSubmitEvent) {
                Toast.makeText(activity, R.string.group_lesson_created, Toast.LENGTH_SHORT).show()


                viewModel.submitGroupLesson(
                    args.groupLesson.id,
                    title.toString(),
                    GroupLessonTypeConverter().toGroupLessonType(type.uppercase()),
                    description.toString(),
                    startDate.plusHours(
                            if(startTimeChanged) timePickerStartTime.hour.toLong()
                            else binding.groupLessonStartTimeUpdate.text.toString().split(":")[0].toLong()
                        )
                        .plusMinutes(
                            if(startTimeChanged) timePickerStartTime.minute.toLong()
                            else binding.groupLessonStartTimeUpdate.text.toString().split(":")[1].toLong()
                        ),
                    // MaterialDateConverter().toDate(binding.groupLessonEndDate.text.toString()).plusHours(timePickerEndTime.hour.toLong()).plusMinutes(timePickerEndTime.minute.toLong()),
                    startDate.plusHours(
                            if(endTimeChanged) timePickerEndTime.hour.toLong()
                            else binding.groupLessonEndTimeUpdate.text.toString().split(":")[0].toLong()
                        )
                        .plusMinutes(
                            if(endTimeChanged) timePickerEndTime.minute.toLong()
                            else binding.groupLessonEndTimeUpdate.text.toString().split(":")[1].toLong()
                        ),
                    Integer.parseInt(participants.toString())
                )

                //Log.i("START DATE", datePickerStartDate.selection.toString())
                Toast.makeText(activity, R.string.group_lesson_saved, Toast.LENGTH_SHORT).show()


                // var test = datePickerStartDate.selection
                // var date = mdateConverter.dateLongToLocalDateTimeConversion(test)
                /*
                val now = LocalDateTime.now()
                val zone = ZoneId.of("Europe/Brussels")
                val zoneOffSet: ZoneOffset = zone.rules.getOffset(now)
                val tim = LocalDateTime.ofEpochSecond(datePickerStartDate.selection!!, 0, zoneOffSet)
                */
                // Log.i("START DATE FOR REAL",  date.year.toString() + "/" + date.monthValue.toString() + "/" + date.dayOfMonth)

                view?.findNavController()
                    ?.navigate(UpdateGroupLessonFragmentDirections.actionUpdateGroupLessonFragmentToGroupLessonsOverviewFragment())
                viewModel.submitEventDone()
            }
        }

        return binding.root
    }
}