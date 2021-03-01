package es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iessaladillo.pedrojoya.stroop.NO_AVATAR_SELECTED
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepository
import es.iessaladillo.pedrojoya.stroop.extensions.getValue
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class PlayerCreationViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val application: Application
) : ViewModel() {


    private val _avatarSelected: MutableLiveData<Int> = MutableLiveData(R.drawable.logo)
    val avatarSelected: LiveData<Int> = _avatarSelected

    val playerName: MutableLiveData<String> = MutableLiveData()

    val valid: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        //    addSource(avatarSelected) { checkAvatar(it) }
        addSource(playerName) {
            value = isValidData()
        }
        addSource(avatarSelected) {
            value = isValidData()
        }
    }

    private val _avatarSelectedPosition: MutableLiveData<Int> = MutableLiveData(NO_AVATAR_SELECTED)
    val avatarSelectedPosition: LiveData<Int>
        get() = _avatarSelectedPosition


    //Events

    private val _onplayerInserted: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onPlayerInserted: LiveData<Event<Boolean>> = _onplayerInserted

    private val _onShowMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val onShowMessage: LiveData<Event<String>>
        get() = _onShowMessage


    fun selectAvatar(position: Int) {
        _avatarSelectedPosition.value = position
    }

    private fun isValidData() =
        isValidName(playerName.getValue("")) && checkAvatar(avatarSelected.getValue(R.drawable.logo))

    private fun isValidName(name: String?) = name.isNullOrBlank().not()

    private fun checkAvatar(avatar: Int?): Boolean {
        return avatar != R.drawable.logo
    }

    fun changeAvatarSelected(avatar: Int) {
        _avatarSelected.value = avatar
    }


    //This function only execute if avatar and name are correct.
    fun createPlayer() {
        val name = playerName.value as String
        val avatar = avatarSelected.value as Int
        val newPlayer = Player(0, name, avatar)
        thread {
            try {
                playerRepository.insertPlayer(newPlayer)
            } catch (e: Exception) {
                _onShowMessage.postValue(Event(application.getString(R.string.player_creation_error_inserting_player)))
                return@thread
            }
            _onplayerInserted.postValue(Event(true))
        }
    }


}