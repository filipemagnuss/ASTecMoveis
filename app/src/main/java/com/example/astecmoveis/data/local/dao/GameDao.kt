package com.example.astecmoveis.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.astecmoveis.data.local.entities.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Query("SELECT * FROM games ORDER BY title ASC")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE id = :gameId")
    fun getGameById(gameId: Int): Flow<GameEntity?>

    @Update // Adicionado método
    suspend fun updateGame(game: GameEntity)

    @Query("UPDATE games SET isPlayed = :isPlayed WHERE id = :gameId")
    suspend fun updatePlayedStatus(gameId: Int, isPlayed: Boolean)

    @Delete
    suspend fun deleteGame(game: GameEntity)
}