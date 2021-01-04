package es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepository

@Suppress("UNCHECKED_CAST")
class PlayerCreationViewModelFactory(private val playerRepository: PlayerRepository,private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlayerCreationViewModel(playerRepository,application) as T
}