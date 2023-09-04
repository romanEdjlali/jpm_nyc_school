package com.roman.jpmnycsch.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
* Class: AppDatabase
* Owner: Roman Edjlali
* Date Created: 08/31/2023 19:01
*/
@Database(entities = [SATScore::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun satScoreDao(): SATScoreDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "appdatabase"
        ).build()
    }
}
