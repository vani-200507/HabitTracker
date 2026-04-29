package com.example.habittracker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.example.habittracker.model.Habit

class HabitAdapter(
    private val habitItems: ArrayList<Habit>,
    private val onAddClick: (Habit, Int) -> Unit,
    private val onReduceClick: (Habit, Int) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgIcon: ImageView = view.findViewById(R.id.ivIcon)
        val txtTitle: TextView = view.findViewById(R.id.tvName)
        val txtDesc: TextView = view.findViewById(R.id.tvDescription)
        val progressHabit: ProgressBar = view.findViewById(R.id.progressBar)
        val txtProgress: TextView = view.findViewById(R.id.tvProgress)
        val txtStatus: TextView = view.findViewById(R.id.tvStatus)
        val btnMinus: Button = view.findViewById(R.id.btnMinus)
        val btnPlus: Button = view.findViewById(R.id.btnPlus)
        val stripDone: View = view.findViewById(R.id.vStripCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_card, parent, false)
        return HabitViewHolder(view)
    }

    override fun getItemCount(): Int = habitItems.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val data = habitItems[position]

        holder.apply {
            txtTitle.text = data.name
            txtDesc.text = data.description
            imgIcon.setImageResource(data.icon)

            progressHabit.max = data.goal
            progressHabit.progress = data.progress

            // hanya tampil: 5 / 5 Jam
            txtProgress.text = "${data.progress} / ${data.goal} ${data.unit}"

            val selesai = data.progress >= data.goal

            if (selesai) {
                txtStatus.text = "Selesai"
                txtStatus.setBackgroundColor(Color.parseColor("#4CAF50"))
                btnPlus.isEnabled = false
                btnMinus.isEnabled = false
                stripDone.visibility = View.VISIBLE
            } else {
                txtStatus.text = "Berjalan"
                txtStatus.setBackgroundColor(Color.parseColor("#FF9800"))
                btnPlus.isEnabled = true
                btnMinus.isEnabled = data.progress > 0
                stripDone.visibility = View.GONE
            }

            btnPlus.setOnClickListener {
                if (data.progress < data.goal) {
                    onAddClick(data, position)
                }
            }

            btnMinus.setOnClickListener {
                if (data.progress > 0) {
                    onReduceClick(data, position)
                }
            }
        }
    }

    fun updateData(newData: List<Habit>) {
        habitItems.clear()
        habitItems.addAll(newData)
        notifyDataSetChanged()
    }
}