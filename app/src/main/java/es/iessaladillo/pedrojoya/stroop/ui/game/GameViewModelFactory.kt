package es.iessaladillo.pedrojoya.stroop.ui.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.repository.GameRepository

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(private val application: Application, private val gameRepositoryImp: GameRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = GameViewModel(application, gameRepositoryImp) as T

}
