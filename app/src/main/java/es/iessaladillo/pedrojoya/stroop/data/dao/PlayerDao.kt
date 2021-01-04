package es.iessaladillo.pedrojoya.stroop.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player


@Dao
interface PlayerDao {

    @Query("SELECT * FROM player WHERE id = :id")
    fun queryCurrentPlayer(id:Long):LiveData<Player?>

    @Query("SELECT * FROM player")
    fun queryAllPlayers():LiveData<List<Player>>

    @Insert
    fun insertPlayer(player: Player):Long

    @Update
    fun updatePlayer(player: Player)

    @Query("Delete from player where id = :playerId")
    fun deletePlayer(playerId: Long)

}