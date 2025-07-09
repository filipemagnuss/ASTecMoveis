package com.example.astecmoveis.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astecmoveis.data.local.entities.GameEntity

class GameAdapter(
    private val onItemClick: (GameEntity) -> Unit,
    private val onTogglePlayed: (GameEntity) -> Unit,
    private val onDeleteClick: (GameEntity) -> Unit,
    private val onEditClick: (GameEntity) -> Unit // Callback para edição
) : ListAdapter<GameEntity, GameViewHolder>(GameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder.from(parent, onItemClick, onTogglePlayed, onDeleteClick, onEditClick)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}