package com.example.astecmoveis.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.astecmoveis.data.local.entities.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: GameEntity)

    @Query("SELECT * FROM games")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("UPDATE games SET isPlayed = :isPlayed WHERE id = :gameId")
    suspend fun updatePlayedStatus(gameId: Int, isPlayed: Boolean)

    @Delete
    suspend fun delete(game: GameEntity)
}
