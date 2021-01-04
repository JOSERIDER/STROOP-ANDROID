package es.iessaladillo.pedrojoya.stroop.ui.player.playerEdition

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.*
import es.iessaladillo.pedrojoya.stroop.*
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepository
import es.iessaladillo.pedrojoya.stroop.extensions.getValue
import kotlin.concurrent.thread

class PlayerEditionViewModel(
    private val playerRepository: PlayerRepository,
    private val application: Application
) : ViewModel() {

    private val preferences: SharedPreferences by lazy {
        application.getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    }

    private val _currentPlayerId: MutableLiveData<Long> = MutableLiveData(NO_PLAYER)

    private val _currentPlayer: LiveData<Player?> = _currentPlayerId.switchMap { playerId ->
        playerRepository.queryCurrentPlayer(playerId)
    }
    val currentPlayer: LiveData<Player?> = _currentPlayer
    val playerName: MutableLiveData<String> = MutableLiveData()

    private val _playerAvatar: MutableLiveData<Int> = MutableLiveData()
    val playerAvatar: LiveData<Int> = _playerAvatar


    private val _avatarSelectedPosition: MutableLiveData<Int> = MutableLiveData(NO_AVATAR_SELECTED)
    val avatarSelectedPosition: LiveData<Int> = _avatarSelectedPosition


    val valid: LiveData<Boolean> = playerName.map {
        !it.isNullOrBlank()
    }

    private val _onPlayerInserted: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onPlayerInserted: LiveData<Event<Boolean>> = _onPlayerInserted


    private val _onShowMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val onShowMessage: LiveData<Event<String>>
        get() = _onShowMessage


    //Setting the values when the current player is available
    fun setValues() {
        val currentPlayer = currentPlayer.value as Player
        playerName.value = currentPlayer.name
        _playerAvatar.value = currentPlayer.avatarResId
        _avatarSelectedPosition.value = avatars.indexOf(currentPlayer.avatarResId)
    }


    fun selectAvatar(position: Int, avatarResId: Int) {
        _avatarSelectedPosition.value = position
        _playerAvatar.value = avatarResId
    }

    fun setCurrentPlayerId(playerId: Long) {
        _currentPlayerId.value = playerId
    }

    fun savePlayer() {
        val playerName = playerName.value!!
        val avatar = _playerAvatar.value!!
        val newPlayer = currentPlayer.value?.copy(name = playerName, avatarResId = avatar)!!
        thread {
            try {
                playerRepository.updatePlayer(newPlayer)
            } catch (e: Exception) {
                _onShowMessage.postValue(Event(application.getString(R.string.player_creation_error_inserting_player)))
                return@thread
            }
            _onPlayerInserted.postValue(Event(true))
        }
    }

    fun deletePlayer() {
        thread {
            try {
                playerRepository.deletePlayer(_currentPlayerId.getValue(NO_PLAYER))
                deleteCurrentPlayerPref()
            } catch (e: Exception) {
                _onShowMessage.postValue(Event(application.getString(R.string.player_creation_error_inserting_player)))
                return@thread
            }
        }
    }

    private fun deleteCurrentPlayerPref() {
        preferences.edit().putLong(PREF_KEY_CURRENT_PLAYER_ID_KEY, NO_PLAYER).apply()
    }
}