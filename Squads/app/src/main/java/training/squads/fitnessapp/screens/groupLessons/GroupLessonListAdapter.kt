package training.squads.fitnessapp.screens.groupLessons

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import training.squads.fitnessapp.R
import training.squads.fitnessapp.databinding.GroupLessonListItemBinding
import training.squads.fitnessapp.screens.groupLessons.GroupLessonListAdapter.GroupLessonListViewHolder
import training.squads.fitnessapp.domain.GroupLesson
import training.squads.fitnessapp.domain.GroupLessonType
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

// TODO: Refactor group lessons overview list adapter
class GroupLessonListAdapter(
    val clickGroupLessonListener: GroupLessonsListener,
    val clickGroupLessonDeleteListener: GroupLessonDeleteListener,
    val clickGroupLessonUpdateListener: GroupLessonUpdateListener): ListAdapter<GroupLesson, GroupLessonListViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<GroupLesson>() {
        override fun areItemsTheSame(oldItem: GroupLesson, newItem: GroupLesson): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GroupLesson, newItem: GroupLesson): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class GroupLessonListViewHolder(private var binding: GroupLessonListItemBinding): RecyclerView.ViewHolder(binding.root) {

        //fields
        val sesTitle: TextView = binding.groupLessonTitle
        val sesDate: TextView = binding.groupLessonDate
        val sesTime: TextView = binding.groupLessonTime
        val sesType: ImageView = binding.groupLessonType

        fun bind(
            groupLessonListener: GroupLessonsListener,
            groupLessonDeleteListener: GroupLessonDeleteListener,
            groupLessonUpdateListener: GroupLessonUpdateListener,
            item: GroupLesson
        ) {
            binding.groupLesson = item
            binding.clickGroupLessonListener = groupLessonListener
            binding.clickGroupLessonDeleteListener = groupLessonDeleteListener
            binding.clickGroupLessonUpdateListener = groupLessonUpdateListener
            binding.executePendingBindings()

            sesTitle.text = item.title


            val dateFormatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.from(ZoneOffset.UTC))
            val timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.from(ZoneOffset.UTC))
            sesDate.text = "Datum: " + dateFormatter.format(item.startDateTime)
            sesTime.text = "Uur: " + timeFormatter.format(item.startDateTime) + " - " + timeFormatter.format(item.endDateTime)

            if (item.type == GroupLessonType.TRAINING) {
                sesType.setImageResource(R.drawable.ic_baseline_fitness_center_24)
            } else {
                sesType.setImageResource(R.drawable.ic_baseline_self_improvement_24)
            }
        }

        companion object {
            fun from(parent: ViewGroup): GroupLessonListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GroupLessonListItemBinding.inflate(layoutInflater, parent, false)
                return GroupLessonListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupLessonListViewHolder {
        return GroupLessonListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GroupLessonListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickGroupLessonListener, clickGroupLessonDeleteListener, clickGroupLessonUpdateListener, item)
    }
}

class GroupLessonsListener(val clickListener: (groupLesson: GroupLesson) -> Unit) {
    fun onClick(groupLesson: GroupLesson) = clickListener(groupLesson)
}

class GroupLessonDeleteListener(val clickDeleteListener: (groupLesson: GroupLesson) -> Unit) {
    fun onClick(groupLesson: GroupLesson) = clickDeleteListener(groupLesson)
}

class GroupLessonUpdateListener(val projectChangeListener: (groupLesson: GroupLesson) -> Unit) {
    fun onClick(groupLesson: GroupLesson) = projectChangeListener(groupLesson)
}


