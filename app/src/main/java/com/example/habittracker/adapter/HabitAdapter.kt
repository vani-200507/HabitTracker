package com.example.habittracker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.HabitCardBinding
import com.example.habittracker.model.Habit
import com.example.habittracker.handler.HabitHandler

class HabitAdapter(
    private val habitItems: ArrayList<Habit>,
    private val onAddClick: (Habit) -> Unit,
    private val onReduceClick: (Habit) -> Unit,
    private val onDeleteClick: (Habit) -> Unit,
    private val onEditClick: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

    class ViewHolder(val binding: HabitCardBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = HabitCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = habitItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val habit = habitItems[position]

        holder.binding.habit = habit
        holder.binding.handler = HabitHandler(

            onAdd = { onAddClick(it) },

            onMinus = { onReduceClick(it) },

            onEdit = { onEditClick(it) }

        )

        holder.binding.ivIcon.setImageResource(habit.icon)

        holder.binding.progressBar.max = habit.goal
        holder.binding.progressBar.progress = habit.progress

        if (habit.progress >= habit.goal) {

            holder.binding.tvStatus.text = "Selesai"

            holder.binding.tvStatus.setBackgroundColor(
                Color.parseColor("#4CAF50")
            )

        } else {

            holder.binding.tvStatus.text = "Berjalan"

            holder.binding.tvStatus.setBackgroundColor(
                Color.parseColor("#FF9800")
            )

        }

        holder.binding.root.setOnLongClickListener {

            onDeleteClick(habit)

            true
        }


        holder.binding.executePendingBindings()
    }

    fun updateData(newData: List<Habit>) {
        habitItems.clear()
        habitItems.addAll(newData)
        notifyDataSetChanged()
    }

}