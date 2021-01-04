package es.iessaladillo.pedrojoya.stroop.data.dao

import androidx.room.Dao
import androidx.room.Insert
import es.iessaladillo.pedrojoya.stroop.data.pojo.Game

@Dao
interface GameDao {

    @Insert
    fun insertGame(game:Game):Long

}