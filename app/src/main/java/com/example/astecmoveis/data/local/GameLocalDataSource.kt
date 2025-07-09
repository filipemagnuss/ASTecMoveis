package com.example.astecmoveis.data.local

import com.example.astecmoveis.data.local.dao.GameDao
import com.example.astecmoveis.data.local.entities.GameEntity
import kotlinx.coroutines.flow.Flow

class GameLocalDataSource(private val gameDao: GameDao) {
    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    suspend fun addGame(game: GameEntity) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: GameEntity) {
        gameDao.deleteGame(game)
    }

    suspend fun checkPlayedGame(gameId: Int, isPlayed: Boolean) {
        gameDao.updatePlayedStatus(gameId, !isPlayed)
    }

    fun getGameById(id: Int): Flow<GameEntity?> = gameDao.getGameById(id)

    suspend fun updateGame(game: GameEntity) = gameDao.updateGame(game)
}