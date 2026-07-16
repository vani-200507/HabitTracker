package com.example.habittracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.habittracker.model.Habit
import com.example.habittracker.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Habit::class,
        User::class
    ],
    version = 2,
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun buildDatabase(context: Context): HabitDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {

                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            CoroutineScope(Dispatchers.IO).launch {

                                INSTANCE?.userDao()?.insertUser(
                                    User(
                                        username = "student",
                                        password = "123"
                                    )
                                )

                            }
                        }

                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}