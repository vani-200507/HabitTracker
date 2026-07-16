package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.habittracker.data.HabitDatabase
import com.example.habittracker.model.Habit
import kotlinx.coroutines.launch

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = HabitDatabase
        .buildDatabase(application)
        .habitDao()

    val habits = dao.getAllHabit()

    fun insertHabit(habit: Habit) {

        viewModelScope.launch {
            dao.insertHabit(habit)
        }

    }

    fun updateHabit(habit: Habit) {

        viewModelScope.launch {
            dao.updateHabit(habit)
        }

    }

    fun deleteHabit(habit: Habit) {

        viewModelScope.launch {
            dao.deleteHabit(habit)
        }

    }

}