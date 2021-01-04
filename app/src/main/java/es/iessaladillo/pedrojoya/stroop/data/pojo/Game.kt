package es.iessaladillo.pedrojoya.stroop.data.pojo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Player::class,
            parentColumns = ["id"],
            childColumns = ["player"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val points: Int,
    val player: Long,
    val gameMode: String,
    val words: Int,
    val correct: Int,
    val incorrect: Int,
    val minutes: Int
)