package es.iessaladillo.pedrojoya.stroop.ui.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iessaladillo.pedrojoya.stroop.NO_PLAYER
import es.iessaladillo.pedrojoya.stroop.PREF_KEY_CURRENT_PLAYER_ID_KEY
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.base.getLongLiveData
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepository
import javax.inject.Inject

@HiltViewModel
class DashboardFragmentViewModel @Inject constructor(private val playerRepository: PlayerRepository, private val application: Application):ViewModel() {

    private val preferences: SharedPreferences by lazy {
        application.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    }

    val currentPlayer:LiveData<Player?> =  preferences.getLongLiveData(
        PREF_KEY_CURRENT_PLAYER_ID_KEY,
        NO_PLAYER
    ).switchMap {playerId ->
        playerRepository.queryCurrentPlayer(playerId)
    }

    private val _newDestination:MutableLiveData<Event<Int>> = MutableLiveData()
    val newDestination:LiveData<Event<Int>>
        get() = _newDestination

    fun setNewDestination(destinationId:Int){
        _newDestination.value = Event(destinationId)
    }

}