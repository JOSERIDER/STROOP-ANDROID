package es.iessaladillo.pedrojoya.stroop.data.repository

import es.iessaladillo.pedrojoya.stroop.data.dao.GameDao
import es.iessaladillo.pedrojoya.stroop.data.pojo.Game

class GameRepositoryImp(private val gameDao: GameDao):GameRepository {

    override fun insertGame(game: Game) : Long  = gameDao.insertGame(game)
}