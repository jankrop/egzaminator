package com.example.egzaminator.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Attempt::class, CorrectQuestion::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun attemptDao(): AttemptDao
    abstract fun correctQuestionDao(): CorrectQuestionDao
}