package com.example.astecmoveis.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.astecmoveis.data.local.entities.GameEntity

class GameDiffCallback : DiffUtil.ItemCallback<GameEntity>() {
    override fun areItemsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
        return oldItem == newItem
    }
}