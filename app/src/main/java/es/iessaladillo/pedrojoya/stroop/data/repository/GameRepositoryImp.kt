package es.iessaladillo.pedrojoya.stroop.data.repository

import es.iessaladillo.pedrojoya.stroop.data.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.pojo.Game
import javax.inject.Inject

class GameRepositoryImp @Inject constructor(private val gameDao: GameDao):GameRepository {

    override fun insertGame(game: Game) : Long  = gameDao.insertGame(game)
}