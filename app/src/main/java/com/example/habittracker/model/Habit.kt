package com.example.habittracker.model

data class Habit(
    val id: String = java.util.UUID.randomUUID().toString(),
    var name: String,
    var description: String,
    var goal: Int,
    var progress: Int,
    var unit: String,
    var icon: Int
)