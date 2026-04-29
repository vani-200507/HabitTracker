package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.habittracker.data.HabitRepository
import com.example.habittracker.model.Habit

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val habitRepo = HabitRepository.getInstance(application)

    val habits: LiveData<ArrayList<Habit>> = habitRepo.habits

    fun insertHabit(habit: Habit) {
        habitRepo.insertHabit(habit)
    }

    fun changeProgress(habit: Habit, progressBaru: Int) {
        habitRepo.changeProgress(habit, progressBaru)
    }
}