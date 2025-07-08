import androidx.room.Entity
import androidx.room.PrimaryKey

data class Game(
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

object GameMapper {
    fun fromEntity(entity: GameEntity) = Game(
        id = entity.id,
        title = entity.title,
        description = entity.description,
        genre = entity.genre,
        developer = entity.developer,
        releaseYear = entity.releaseYear,
        rating = entity.rating,
        isPlayed = entity.isPlayed,
        createdAt = entity.createdAt,
        addedBy = entity.addedBy,
        imageUrl = entity.imageUrl
    )

    fun toEntity(game: Game) = GameEntity(
        id = game.id,
        title = game.title,
        description = game.description,
        genre = game.genre,
        developer = game.developer,
        releaseYear = game.releaseYear,
        rating = game.rating,
        isPlayed = game.isPlayed,
        createdAt = game.createdAt,
        addedBy = game.addedBy,
        imageUrl = game.imageUrl
    )
}
