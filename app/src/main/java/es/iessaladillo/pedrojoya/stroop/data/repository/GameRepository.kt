package es.iessaladillo.pedrojoya.stroop.data.repository

import es.iessaladillo.pedrojoya.stroop.data.pojo.Game

interface GameRepository {

    fun insertGame(game:Game):Long
}