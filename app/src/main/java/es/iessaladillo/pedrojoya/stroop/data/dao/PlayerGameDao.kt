package es.iessaladillo.pedrojoya.stroop.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import es.iessaladillo.pedrojoya.stroop.data.pojo.PlayerGame

@Dao
interface PlayerGameDao {

    @Transaction
    @Query("Select * from game order by points desc limit 10")
    fun queryAllGames(): LiveData<List<PlayerGame>>


    @Transaction
    @Query("Select * from  game where gameMode = 'Time' order by points desc limit 10 ")
    fun queryTimeGames(): LiveData<List<PlayerGame>>


    @Transaction
    @Query("Select * from  game where gameMode = 'Attemps' order by points desc limit 10 ")
    fun queryAttempsGame(): LiveData<List<PlayerGame>>

    @Transaction
    @Query("Select * from game where gameMode = :gameMode order by points desc limit 10")
    fun queryGamesByFilter(gameMode: String): LiveData<List<PlayerGame>>


    @Transaction
    @Query("Select *  from game where id = :gameId")
    fun queryGame(gameId: Long): LiveData<PlayerGame>
}