package es.iessaladillo.pedrojoya.stroop.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerGameRepository

@Suppress("UNCHECKED_CAST")
class ResultFragmentViewModelFactory(private val playerGameRepository: PlayerGameRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ResultFragmentViewModel(playerGameRepository) as T


}