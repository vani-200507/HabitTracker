package com.example.habittracker.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.Habit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitRepository private constructor(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("habit_storage", Context.MODE_PRIVATE)

    private val gsonParser = Gson()

    private val _habits = MutableLiveData<ArrayList<Habit>>(arrayListOf())
    val habits: MutableLiveData<ArrayList<Habit>> = _habits

    init {
        loadHabits()
    }

    private fun loadHabits() {
        val data = sharedPref.getString("habit_list", null)

        if (data != null) {
            val type = object : TypeToken<ArrayList<Habit>>() {}.type
            val list: ArrayList<Habit> = gsonParser.fromJson(data, type)
            _habits.value = list
        } else {
            _habits.value = arrayListOf()
        }
    }

    private fun saveHabits() {
        val json = gsonParser.toJson(_habits.value)
        sharedPref.edit().putString("habit_list", json).apply()
    }

    fun insertHabit(habit: Habit) {
        val list = ArrayList(_habits.value ?: arrayListOf())
        list.add(habit)
        _habits.value = list
        saveHabits()
    }

    fun changeProgress(target: Habit, progressBaru: Int) {
        val list = _habits.value ?: return

        val index = list.indexOfFirst {
            it.name == target.name && it.description == target.description
        }

        if (index != -1) {
            val updated = list[index].copy(progress = progressBaru)
            val newList = ArrayList(list)
            newList[index] = updated
            _habits.value = newList
            saveHabits()
        }
    }

    fun deleteAll() {
        _habits.value = arrayListOf()
        saveHabits()
    }

    companion object {
        @Volatile
        private var instance: HabitRepository? = null

        fun getInstance(context: Context): HabitRepository {
            return instance ?: synchronized(this) {
                instance ?: HabitRepository(context.applicationContext).also { instance = it }
            }
        }
    }
}