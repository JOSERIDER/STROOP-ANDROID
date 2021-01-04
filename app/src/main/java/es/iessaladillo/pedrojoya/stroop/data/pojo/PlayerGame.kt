package es.iessaladillo.pedrojoya.stroop.data.pojo

import androidx.room.Embedded
import androidx.room.Relation


data class PlayerGame (
    @Embedded
    val game:Game,
    @Relation(
        parentColumn = "player",
        entityColumn = "id"
    )
    val player: Player
)