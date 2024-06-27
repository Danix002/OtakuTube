package com.example.anitest.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlaylistEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): PlaylistDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "OtakuTubeDB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}