package ali.hrhera.base.adapter

import androidx.recyclerview.widget.DiffUtil

class DiffUtilItemCallback<T : Any>(
    private val areItemsSame: (oldItem: T, newItem: T) -> Boolean,
    private val areContentsSame: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return areItemsSame(oldItem, newItem)
    }
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentsSame(oldItem, newItem)
    }
}