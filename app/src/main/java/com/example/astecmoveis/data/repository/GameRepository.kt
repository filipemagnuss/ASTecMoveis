package com.example.astecmoveis.data.repository

import com.example.astecmoveis.data.local.GameLocalDataSource
import com.example.astecmoveis.data.local.entities.GameEntity
import kotlinx.coroutines.flow.Flow

class GameRepository(private val localDataSource: GameLocalDataSource) {

    fun getAllGames(): Flow<List<GameEntity>> = localDataSource.getAllGames()

    suspend fun addGame(game: GameEntity) = localDataSource.addGame(game)

    suspend fun deleteGame(game: GameEntity) = localDataSource.deleteGame(game)

    suspend fun togglePlayedStatus(gameId: Int, currentStatus: Boolean) =
        localDataSource.checkPlayedGame(gameId, currentStatus)

    fun getGameById(id: Int): Flow<GameEntity?> = localDataSource.getGameById(id)

    suspend fun updateGame(game: GameEntity) = localDataSource.updateGame(game)
}