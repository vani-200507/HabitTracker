package com.example.habittracker.view

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.model.Habit
import com.example.habittracker.viewmodel.HabitViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddHabitFragment : Fragment(R.layout.fragment_add_habit) {

    private val viewModel: HabitViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<TextInputEditText>(R.id.etHabitName)
        val etDesc = view.findViewById<TextInputEditText>(R.id.etDescription)
        val etGoal = view.findViewById<TextInputEditText>(R.id.etGoal)
        val etUnit = view.findViewById<TextInputEditText>(R.id.etUnit)
        val dropdownIcon = view.findViewById<AutoCompleteTextView>(R.id.actvSelectIcon)
        val btnSave = view.findViewById<MaterialButton>(R.id.btnCreateHabit)

        setupDropdown(dropdownIcon)

        btnSave.setOnClickListener {

            val name = etName.text.toString().trim()
            val desc = etDesc.text.toString().trim()
            val goalText = etGoal.text.toString().trim()
            val unit = etUnit.text.toString().trim()
            val iconName = dropdownIcon.text.toString()

            if (name.isEmpty() || desc.isEmpty() || goalText.isEmpty() || unit.isEmpty()) {
                Toast.makeText(requireContext(),
                    "Semua data harus diisi!",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val iconRes = getIcon(iconName)

            val habit = Habit(
                name = name,
                description = desc,
                goal = goalText.toInt(),
                progress = 0,
                unit = unit,
                icon = iconRes
            )

            viewModel.insertHabit(habit)

            Toast.makeText(requireContext(),
                "Habit berhasil disimpan!",
                Toast.LENGTH_SHORT).show()

            findNavController().navigateUp()
        }
    }

    private fun setupDropdown(dropdown: AutoCompleteTextView) {
        val options = listOf(
            "Workout",
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
            options
        )

        dropdown.setAdapter(adapter)
        dropdown.setText(options[0], false)
    }

    private fun getIcon(name: String): Int {
        return when (name) {
            "Belajar" -> R.drawable.ic_study
            "Minum Air" -> R.drawable.ic_water
            "Makan Sehat" -> R.drawable.ic_food
            "Membaca" -> R.drawable.ic_reading
            "Tidur" -> R.drawable.ic_sleep
            "Relaksasi" -> R.drawable.ic_meditation
            else -> R.drawable.ic_study
        }
    }
}