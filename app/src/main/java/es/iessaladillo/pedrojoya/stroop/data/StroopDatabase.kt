package es.iessaladillo.pedrojoya.stroop.data

import androidx.room.Database
import androidx.room.RoomDatabase
import es.iessaladillo.pedrojoya.stroop.data.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerGameDao
import es.iessaladillo.pedrojoya.stroop.data.pojo.Game
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player

@Database(entities = [Player::class, Game::class], version = 1)
abstract class StroopDatabase : RoomDatabase() {

    abstract val playerDao: PlayerDao
    abstract val playerGameDao: PlayerGameDao
    abstract val game: GameDao

}