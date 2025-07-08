package com.example.astecmoveis.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astecmoveis.data.local.entities.GameEntity
import com.example.astecmoveis.data.repository.GameRepository
import kotlinx.coroutines.launch


class GameViewModel(private val repository: GameRepository) : ViewModel() {

    // LiveData para a lista de jogos que será observada pela UI
    private val _games = MutableLiveData<List<GameEntity>>()
    val games: LiveData<List<GameEntity>> get() = _games

    // LiveData para um jogo específico (por exemplo, para a tela de detalhes)
    private val _currentGame = MutableLiveData<GameEntity?>()
    val currentGame: LiveData<GameEntity?> get() = _currentGame

    // LiveData para mensagens de erro ou sucesso para a UI
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    // Inicializador do ViewModel. É um bom lugar para carregar dados iniciais.
    init {
        loadAllGames()
    }

    // --- Funções para interagir com o repositório ---

    fun loadAllGames() {
        viewModelScope.launch {
            try {
                _games.value = repository.getAllGames()
            } catch (e: Exception) {
                _message.value = "Erro ao carregar jogos: ${e.message}"
            }
        }
    }

    fun getGameById(id: Int) {
        viewModelScope.launch {
            try {
                _currentGame.value = repository.getGameById(id)
            } catch (e: Exception) {
                _message.value = "Erro ao carregar jogo: ${e.message}"
            }
        }
    }

    fun addGame(game: GameEntity) {
        viewModelScope.launch {
            try {
                repository.insertGame(game)
                _message.value = "Jogo adicionado com sucesso!"
                loadAllGames() // Recarrega a lista após adicionar um novo jogo
            } catch (e: Exception) {
                _message.value = "Erro ao adicionar jogo: ${e.message}"
            }
        }
    }

    fun updateGame(game: GameEntity) {
        viewModelScope.launch {
            try {
                repository.updateGame(game)
                _message.value = "Jogo atualizado com sucesso!"
                loadAllGames() // Recarrega a lista após atualizar um jogo
            } catch (e: Exception) {
                _message.value = "Erro ao atualizar jogo: ${e.message}"
            }
        }
    }

    fun deleteGame(game: GameEntity) {
        viewModelScope.launch {
            try {
                repository.deleteGame(game)
                _message.value = "Jogo excluído com sucesso!"
                loadAllGames() // Recarrega a lista após excluir um jogo
            } catch (e: Exception) {
                _message.value = "Erro ao excluir jogo: ${e.message}"
            }
        }
    }
}