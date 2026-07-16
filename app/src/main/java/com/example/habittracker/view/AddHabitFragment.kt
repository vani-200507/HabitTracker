package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentAddHabitBinding
import com.example.habittracker.model.Habit
import com.example.habittracker.viewmodel.HabitViewModel

class AddHabitFragment : Fragment() {

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HabitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        setupDropdown()

        binding.btnCreateHabit.setOnClickListener {

            val name = binding.etHabitName.text.toString().trim()
            val desc = binding.etDescription.text.toString().trim()
            val goalText = binding.etGoal.text.toString().trim()
            val unit = binding.etUnit.text.toString().trim()
            val iconName = binding.actvSelectIcon.text.toString()

            if (
                name.isEmpty() ||
                desc.isEmpty() ||
                goalText.isEmpty() ||
                unit.isEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "Semua data harus diisi!",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val habit = Habit(
                name = name,
                description = desc,
                goal = goalText.toInt(),
                progress = 0,
                unit = unit,
                icon = getIcon(iconName)
            )

            viewModel.insertHabit(habit)

            Toast.makeText(
                requireContext(),
                "Habit berhasil disimpan!",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigateUp()
        }
    }

    private fun setupDropdown() {

        val listIcon = listOf(
            "Belajar",
            "Minum Air",
            "Makan Sehat",
            "Membaca",
            "Tidur",
            "Relaksasi"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listIcon
        )

        binding.actvSelectIcon.setAdapter(adapter)
        binding.actvSelectIcon.setText(listIcon[0], false)
    }

    private fun getIcon(icon: String): Int {

        return when (icon) {

            "Belajar" -> R.drawable.ic_study

            "Minum Air" -> R.drawable.ic_water

            "Makan Sehat" -> R.drawable.ic_food

            "Membaca" -> R.drawable.ic_reading

            "Tidur" -> R.drawable.ic_sleep

            "Relaksasi" -> R.drawable.ic_meditation

            else -> R.drawable.ic_study
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}