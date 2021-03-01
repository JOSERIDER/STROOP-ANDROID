package es.iessaladillo.pedrojoya.stroop.data.repository

import androidx.lifecycle.LiveData
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player
import javax.inject.Inject

class PlayerRepositoryImp @Inject constructor(private val playerDao: PlayerDao) : PlayerRepository {

    override fun queryCurrentPlayer(id: Long): LiveData<Player?> =
        playerDao.queryCurrentPlayer(id)

    override fun queryAllPlayers(): LiveData<List<Player>> = playerDao.queryAllPlayers()

    override fun insertPlayer(player: Player) = playerDao.insertPlayer(player)
    override fun updatePlayer(player: Player) = playerDao.updatePlayer(player)

    override fun deletePlayer(playerId: Long) = playerDao.deletePlayer(playerId)
}