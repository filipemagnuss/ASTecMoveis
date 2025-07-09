package com.example.astecmoveis.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val createdAt: String = System.currentTimeMillis().toString()
) : Parcelable