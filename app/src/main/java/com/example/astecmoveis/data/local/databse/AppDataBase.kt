package com.example.astecmoveis.data.local.database // Pacote corrigido

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.astecmoveis.data.local.dao.GameDao
import com.example.astecmoveis.data.local.entities.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "game_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}