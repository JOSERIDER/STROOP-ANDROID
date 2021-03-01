package es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iessaladillo.pedrojoya.stroop.NO_PLAYER
import es.iessaladillo.pedrojoya.stroop.PREF_KEY_CURRENT_PLAYER_ID_KEY
import es.iessaladillo.pedrojoya.stroop.base.getLongLiveData
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepository
import javax.inject.Inject

@HiltViewModel
class PlayerSelectionViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val application: Application
) : ViewModel() {

    private val preferences: SharedPreferences by lazy {
        application.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    }

     val currentPlayerId: LiveData<Long> =
        preferences.getLongLiveData(PREF_KEY_CURRENT_PLAYER_ID_KEY, NO_PLAYER)

    val currentPlayer: LiveData<Player?> = currentPlayerId.switchMap { playerId ->
        playerRepository.queryCurrentPlayer(playerId)
    }

    val players: LiveData<List<Player>> = playerRepository.queryAllPlayers()

    fun changeCurrentPlayer(playerId: Long) {
        preferences.edit().putLong(PREF_KEY_CURRENT_PLAYER_ID_KEY, playerId).apply()
    }

}