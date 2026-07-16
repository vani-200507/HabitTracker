package com.example.habittracker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.R
import com.example.habittracker.adapter.HabitAdapter
import com.example.habittracker.databinding.FragmentDashboardBinding
import com.example.habittracker.viewmodel.HabitViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HabitViewModel by activityViewModels()

    private lateinit var habitAdapter: HabitAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        observeHabit()

        // Tambah Habit
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_addHabitFragment
            )
        }

        // Logout
        binding.btnLogout.setOnClickListener {

            val pref = requireActivity().getSharedPreferences(
                "habit_pref",
                Context.MODE_PRIVATE
            )

            pref.edit()
                .clear()
                .apply()

            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun initRecycler() {

        habitAdapter = HabitAdapter(

            habitItems = arrayListOf(),

            onAddClick = { habit ->

                if (habit.progress < habit.goal) {
                    habit.progress++
                    viewModel.updateHabit(habit)
                }

            },

            onReduceClick = { habit ->

                if (habit.progress > 0) {
                    habit.progress--
                    viewModel.updateHabit(habit)
                }

            },

            onDeleteClick = { habit ->

                viewModel.deleteHabit(habit)

            },

            onEditClick = { habit ->

                val action =
                    DashboardFragmentDirections
                        .actionDashboardFragmentToEditHabitFragment(habit)

                findNavController().navigate(action)

            }

        )

        binding.rvHabits.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvHabits.adapter = habitAdapter
    }

    private fun observeHabit() {

        viewModel.habits.observe(viewLifecycleOwner) { list ->

            if (list.isEmpty()) {

                binding.rvHabits.visibility = View.GONE
                binding.tvEmptyMessage.visibility = View.VISIBLE

            } else {

                binding.rvHabits.visibility = View.VISIBLE
                binding.tvEmptyMessage.visibility = View.GONE

                habitAdapter.updateData(list)

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}