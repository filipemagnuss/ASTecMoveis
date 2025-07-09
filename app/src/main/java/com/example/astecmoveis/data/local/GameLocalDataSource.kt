package com.example.astecmoveis.data.local

import com.example.astecmoveis.data.local.dao.GameDao
import com.example.astecmoveis.data.local.entities.GameEntity
import kotlinx.coroutines.flow.Flow

class GameLocalDataSource(private val gameDao: GameDao) {
    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    suspend fun addGame(game: GameEntity) {
        gameDao.insertGame(game) // Chamando o método renomeado
    }

    suspend fun deleteGame(game: GameEntity) {
        gameDao.deleteGame(game) // Chamando o método renomeado
    }

    suspend fun checkPlayedGame(gameId: Int, isPlayed: Boolean) {
        // A lógica de inversão (!) de 'isPlayed' estava aqui.
        // Se a intenção era alternar, mantenha. Se for para SETAR o status, remova o '!'.
        // Mantido para compatibilidade, mas avalie a lógica.
        gameDao.updatePlayedStatus(gameId, !isPlayed)
    }

    fun getGameById(id: Int): Flow<GameEntity?> = gameDao.getGameById(id) // Adicionado método

    suspend fun updateGame(game: GameEntity) = gameDao.updateGame(game) // Adicionado método
}