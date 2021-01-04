package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerGameRepository

@Suppress("UNCHECKED_CAST")
class RankingFragmentViewModelFactory(
    private val playerGameRepository: PlayerGameRepository,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = RankingFragmentViewModel(playerGameRepository,application) as T
}