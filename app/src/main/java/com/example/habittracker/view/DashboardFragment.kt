package com.example.habittracker.view

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.example.habittracker.adapter.HabitAdapter
import com.example.habittracker.viewmodel.HabitViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardFragment : Fragment() {

    private val viewModel: HabitViewModel by activityViewModels()
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var recyclerHabit: RecyclerView
    private lateinit var btnAddHabit: FloatingActionButton
    private lateinit var txtEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerHabit = view.findViewById(R.id.rvHabits)
        btnAddHabit = view.findViewById(R.id.fabAdd)
        txtEmpty = view.findViewById(R.id.tvEmptyMessage)

        initRecycler()
        loadData()

        btnAddHabit.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addHabitFragment)
        }
    }

    private fun initRecycler() {
        habitAdapter = HabitAdapter(
            habitItems = arrayListOf(),
            onAddClick = { habit, _ ->
                val updated = habit.progress + 1
                if (updated <= habit.goal) {
                    viewModel.changeProgress(habit, updated)
                }
            },
            onReduceClick = { habit, _ ->
                val updated = habit.progress - 1
                if (updated >= 0) {
                    viewModel.changeProgress(habit, updated)
                }
            }
        )

        recyclerHabit.layoutManager = LinearLayoutManager(requireContext())
        recyclerHabit.adapter = habitAdapter
    }

    private fun loadData() {
        viewModel.habits.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                recyclerHabit.visibility = View.GONE
                txtEmpty.visibility = View.VISIBLE
            } else {
                habitAdapter.updateData(list)
                recyclerHabit.visibility = View.VISIBLE
                txtEmpty.visibility = View.GONE
            }
        }
    }
}