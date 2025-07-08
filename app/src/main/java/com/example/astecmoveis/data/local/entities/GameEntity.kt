package com.example.astecmoveis.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val genre: String = "",
    val developer: String = "",
    val releaseYear: Int,
    val rating: Double,
    val isPlayed: Boolean = false,
    val createdAt: String = System.currentTimeMillis().toString(),
    val addedBy: String = "Não informado",
    val imageUrl: String
)
