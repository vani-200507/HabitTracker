package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habittracker.databinding.FragmentEditHabitBinding
import com.example.habittracker.viewmodel.HabitViewModel

class EditHabitFragment : Fragment(), HabitEditListener {

    private var _binding: FragmentEditHabitBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HabitViewModel by activityViewModels()

    private val args: EditHabitFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditHabitBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.habit = args.habit

        binding.listener = this
    }

    override fun onClick(v: View) {

        val habit = binding.habit!!

        habit.goal = binding.etGoal.text.toString().toInt()

        viewModel.updateHabit(habit)

        Toast.makeText(
            requireContext(),
            "Habit Updated",
            Toast.LENGTH_SHORT
        ).show()

        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}