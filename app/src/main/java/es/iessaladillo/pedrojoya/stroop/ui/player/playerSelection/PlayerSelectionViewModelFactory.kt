package es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepository

@Suppress("UNCHECKED_CAST")
class PlayerSelectionViewModelFactory(private val repository: PlayerRepository, private val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlayerSelectionViewModel(repository,application) as T
}