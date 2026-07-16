package com.example.habittracker.handler

import com.example.habittracker.model.Habit

class HabitHandler(

    private val onAdd: (Habit) -> Unit,
    private val onMinus: (Habit) -> Unit,
    private val onEdit: (Habit) -> Unit

) {

    fun add(habit: Habit) {
        onAdd(habit)
    }

    fun minus(habit: Habit) {
        onMinus(habit)
    }

    fun edit(habit: Habit) {
        onEdit(habit)
    }

}