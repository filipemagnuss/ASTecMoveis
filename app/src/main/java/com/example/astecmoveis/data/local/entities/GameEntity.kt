package com.example.astecmoveis.data.local.entities

import android.os.Parcelable // Importe Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize // Importe Parcelize

@Parcelize // Adicione a anotação Parcelize
@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val genre: String = "",
    val developer: String = "",
    val releaseYear: Int, // Certifique-se de que este campo pode ter um valor padrão ou é preenchido
    val rating: Double, // Certifique-se de que este campo pode ter um valor padrão ou é preenchido
    val isPlayed: Boolean = false,
    val createdAt: String = System.currentTimeMillis().toString(),
    val addedBy: String = "Não informado",
    val imageUrl: String // Certifique-se de que este campo pode ter um valor padrão ou é preenchido
) : Parcelable // Implemente Parcelable