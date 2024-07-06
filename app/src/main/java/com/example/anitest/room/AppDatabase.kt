package com.example.anitest.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PlaylistEntity::class, PlaylistAnimeRelationEntity::class, UserEntity::class, AnimeEntity::class],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoPlaylist(): PlaylistDao
    abstract fun daoUser(): UserDao
    abstract fun daoAnime(): AnimeDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "OtakuTubeDB"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}