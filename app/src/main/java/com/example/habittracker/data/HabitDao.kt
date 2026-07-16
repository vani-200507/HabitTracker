package com.example.habittracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habittracker.model.Habit

@Dao
interface HabitDao {

    @Insert
    suspend fun insertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habit ORDER BY id ASC")
    fun getAllHabit(): LiveData<List<Habit>>
}