package es.iessaladillo.pedrojoya.stroop.data.repository

import androidx.lifecycle.LiveData
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player

interface PlayerRepository {

    fun queryCurrentPlayer(id:Long): LiveData<Player?>

    fun queryAllPlayers(): LiveData<List<Player>>

    fun insertPlayer(player: Player):Long

    fun updatePlayer(player: Player)

    fun deletePlayer(playerId :Long)

}