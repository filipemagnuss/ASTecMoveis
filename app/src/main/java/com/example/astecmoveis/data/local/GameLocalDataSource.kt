package com.example.astecmoveis.data.local

import com.example.astecmoveis.data.local.dao.GameDao
import com.example.astecmoveis.data.local.entities.GameEntity
import kotlinx.coroutines.flow.Flow

class GameLocalDataSource(private val gameDao: GameDao) {
    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    suspend fun addGame(game: GameEntity) {
        gameDao.insert(game)
    }

    suspend fun deleteGame(game: GameEntity) {
        gameDao.delete(game)
    }

    suspend fun checkPlayedGame(gameId: Int, isPlayed: Boolean) {
        gameDao.updatePlayedStatus(gameId, !isPlayed)
    }
}
