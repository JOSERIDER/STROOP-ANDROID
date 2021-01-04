package es.iessaladillo.pedrojoya.stroop.data.repository

import androidx.lifecycle.LiveData
import es.iessaladillo.pedrojoya.stroop.data.pojo.PlayerGame

interface PlayerGameRepository {

    fun queryAllGames(): LiveData<List<PlayerGame>>

    fun queryTimeGames(): LiveData<List<PlayerGame>>

    fun queryAttempsGame(): LiveData<List<PlayerGame>>

    fun queryGamesByFilter(gameMode: String): LiveData<List<PlayerGame>>

    fun queryGame(gameId:Long): LiveData<PlayerGame>


}