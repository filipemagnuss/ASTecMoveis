package com.example.astecmoveis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.astecmoveis.data.local.entities.GameEntity
import com.example.astecmoveis.databinding.GameItemListBinding // Assumindo que o layout é game_item_list.xml

class GameViewHolder(
    private val binding: GameItemListBinding,
    private val onItemClick: (GameEntity) -> Unit,
    private val onTogglePlayed: (GameEntity) -> Unit,
    private val onDeleteClick: (GameEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(game: GameEntity) {
        binding.tvGameTitle.text = game.title // Supondo que você tenha tvGameTitle no game_item_list.xml
        binding.tvGameGenre.text = game.genre // Supondo que você tenha tvGameGenre no game_item_list.xml
        binding.cbPlayed.isChecked = game.isPlayed // Supondo que você tenha um CheckBox cbPlayed

        binding.root.setOnClickListener {
            onItemClick(game)
        }

        binding.cbPlayed.setOnCheckedChangeListener { _, isChecked ->
            onTogglePlayed(game.copy(isPlayed = isChecked)) // Passa o jogo com o novo status
        }

        binding.btnDelete.setOnClickListener { // Supondo que você tenha um botão de exclusão
            onDeleteClick(game)
        }
    }

    companion object {
        fun from(parent: ViewGroup, onItemClick: (GameEntity) -> Unit, onTogglePlayed: (GameEntity) -> Unit, onDeleteClick: (GameEntity) -> Unit): GameViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = GameItemListBinding.inflate(layoutInflater, parent, false)
            return GameViewHolder(binding, onItemClick, onTogglePlayed, onDeleteClick)
        }
    }
}