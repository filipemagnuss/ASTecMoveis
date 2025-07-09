package com.example.astecmoveis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.astecmoveis.data.local.entities.GameEntity
import com.example.astecmoveis.databinding.GameItemListBinding

class GameViewHolder(
    private val binding: GameItemListBinding,
    private val onItemClick: (GameEntity) -> Unit,
    private val onTogglePlayed: (GameEntity) -> Unit,
    private val onDeleteClick: (GameEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(game: GameEntity) {
        binding.tvGameTitle.text = game.title
        binding.tvGameGenre.text = game.genre


        binding.cbPlayed.setOnCheckedChangeListener(null)
        binding.cbPlayed.isChecked = game.isPlayed


        binding.cbPlayed.setOnCheckedChangeListener { _, isChecked ->
            onTogglePlayed(game.copy(isPlayed = isChecked))
        }

        binding.root.setOnClickListener {
            onItemClick(game)
        }

        binding.btnDelete.setOnClickListener {
            onDeleteClick(game)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onItemClick: (GameEntity) -> Unit,
            onTogglePlayed: (GameEntity) -> Unit,
            onDeleteClick: (GameEntity) -> Unit
        ): GameViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = GameItemListBinding.inflate(layoutInflater, parent, false)
            return GameViewHolder(binding, onItemClick, onTogglePlayed, onDeleteClick)
        }
    }
}
