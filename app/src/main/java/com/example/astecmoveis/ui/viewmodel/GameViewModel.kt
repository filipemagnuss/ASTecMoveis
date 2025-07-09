package com.example.astecmoveis.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.astecmoveis.data.local.database.AppDatabase // Importe o caminho corrigido
import com.example.astecmoveis.data.local.GameLocalDataSource
import com.example.astecmoveis.data.local.entities.GameEntity
import com.example.astecmoveis.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    // Usando StateFlow para a lista de jogos que será observada pela UI
    private val _games = MutableStateFlow<List<GameEntity>>(emptyList())
    val games: StateFlow<List<GameEntity>> get() = _games

    // LiveData para um jogo específico (por exemplo, para a tela de detalhes)
    private val _currentGame = MutableLiveData<GameEntity?>()
    val currentGame: LiveData<GameEntity?> get() = _currentGame

    // LiveData para mensagens de erro ou sucesso para a UI
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    init {
        loadAllGames() // Carrega os dados iniciais
    }

    // --- Funções para interagir com o repositório ---

    fun loadAllGames() {
        viewModelScope.launch {
            repository.getAllGames()
                .catch { e ->
                    // Captura qualquer exceção do Flow
                    _message.postValue("Erro ao carregar jogos: ${e.message}")
                }
                .collect { games ->
                    // Coleta os jogos e atualiza o StateFlow
                    _games.value = games
                }
        }
    }

    fun getGameById(id: Int) {
        viewModelScope.launch {
            repository.getGameById(id)
                .catch { e ->
                    _message.postValue("Erro ao carregar jogo: ${e.message}")
                }
                .collect { game ->
                    _currentGame.postValue(game)
                }
        }
    }

    fun addGame(game: GameEntity) {
        viewModelScope.launch {
            try {
                repository.addGame(game) // Usar 'addGame' do repositório
                _message.postValue("Jogo adicionado com sucesso!")
                // O Flow de getAllGames() já se encarrega de atualizar a lista
            } catch (e: Exception) {
                _message.postValue("Erro ao adicionar jogo: ${e.message}")
            }
        }
    }

    fun updateGame(game: GameEntity) {
        viewModelScope.launch {
            try {
                repository.updateGame(game)
                _message.postValue("Jogo atualizado com sucesso!")
                // O Flow de getAllGames() já se encarrega de atualizar a lista
            } catch (e: Exception) {
                _message.postValue("Erro ao atualizar jogo: ${e.message}")
            }
        }
    }

    fun deleteGame(game: GameEntity) {
        viewModelScope.launch {
            try {
                repository.deleteGame(game)
                _message.postValue("Jogo excluído com sucesso!")
                // O Flow de getAllGames() já se encarrega de atualizar a lista
            } catch (e: Exception) {
                _message.postValue("Erro ao excluir jogo: ${e.message}")
            }
        }
    }

    fun togglePlayedStatus(gameId: Int, currentStatus: Boolean) {
        viewModelScope.launch {
            try {
                repository.togglePlayedStatus(gameId, currentStatus)
                _message.postValue("Status do jogo atualizado!")
            } catch (e: Exception) {
                _message.postValue("Erro ao atualizar status: ${e.message}")
            }
        }
    }
}

// --- ViewModel Factory para injetar o repositório ---
class GameViewModelFactory(private val applicationContext: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            // Cria as instâncias necessárias
            val database = AppDatabase.getDatabase(applicationContext)
            val localDataSource = GameLocalDataSource(database.gameDao())
            val repository = GameRepository(localDataSource)
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}