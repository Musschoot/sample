package training.squads.fitnessapp.screens.userProfile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import training.squads.fitnessapp.database.pass.dateConverter
import training.squads.fitnessapp.databinding.PassListItemBinding
import training.squads.fitnessapp.domain.Pass
import training.squads.fitnessapp.utilities.DateConverter

class PassListAdapter(
    val clickPassListener: PassesListener): ListAdapter<Pass, PassListAdapter.PassListViewHolder>(DiffCallback) {

    var dateConverter = DateConverter()

    companion object DiffCallback : DiffUtil.ItemCallback<Pass>() {
        override fun areItemsTheSame(oldItem: Pass, newItem: Pass): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pass, newItem: Pass): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class PassListViewHolder(private var binding: PassListItemBinding): RecyclerView.ViewHolder(binding.root) {

        val creditsPurchaseTime: TextView = binding.creditsPurchaseTime
        val creditsSessions: TextView = binding.creditsSessions
        val creditsPaymentStatus: TextView = binding.creditsPaymentStatus

        fun bind(
            passListener: PassesListener,
            item: Pass
        ) {
            binding.pass = item
            binding.clickPassListener = passListener
            creditsPurchaseTime.text = dateConverter.toJson(item.purchaseTime)
            creditsSessions.text = item.sessions.toString()
            creditsPaymentStatus.text = (if(item.paymentStatus) "Betaald" else "Te betalen")
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PassListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PassListItemBinding.inflate(layoutInflater, parent, false)
                return PassListViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassListViewHolder {
        return PassListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PassListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickPassListener,item)
    }

}

class PassesListener(val clickListener: (pass: Pass) -> Unit) {
    fun onClick(pass: Pass) = clickListener(pass)
}