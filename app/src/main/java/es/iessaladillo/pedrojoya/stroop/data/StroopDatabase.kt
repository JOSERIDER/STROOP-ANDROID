package es.iessaladillo.pedrojoya.stroop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerGameDao
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.pojo.Game
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player
import kotlin.concurrent.thread

@Database(entities = [Player::class, Game::class], version = 1)
abstract class StroopDatabase : RoomDatabase() {

    abstract val playerDao: PlayerDao
    abstract val playerGameDao: PlayerGameDao
    abstract val game: GameDao

    companion object {
        @Volatile
        private var INSTANCE: StroopDatabase? = null

        fun getInstance(context: Context): StroopDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            StroopDatabase::class.java,
                            "stroop_database"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}