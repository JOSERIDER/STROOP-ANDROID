package es.iessaladillo.pedrojoya.stroop.data.repository

import androidx.lifecycle.LiveData
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerGameDao
import es.iessaladillo.pedrojoya.stroop.data.pojo.PlayerGame

class PlayerGameRepositoryImp(private val playerGameDao: PlayerGameDao): PlayerGameRepository {

    override fun queryAllGames(): LiveData<List<PlayerGame>> = playerGameDao.queryAllGames()

    override fun queryTimeGames(): LiveData<List<PlayerGame>> = playerGameDao.queryTimeGames()

    override fun queryAttempsGame(): LiveData<List<PlayerGame>> = playerGameDao.queryAttempsGame()
    
    override fun queryGamesByFilter(gameMode: String): LiveData<List<PlayerGame>> =
        playerGameDao.queryGamesByFilter(gameMode)

    override fun queryGame(gameId: Long): LiveData<PlayerGame> = playerGameDao.queryGame(gameId)

}