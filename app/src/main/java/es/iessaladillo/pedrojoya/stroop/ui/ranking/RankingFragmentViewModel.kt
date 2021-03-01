package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.view.View
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.base.enums.GameMode
import es.iessaladillo.pedrojoya.stroop.data.pojo.PlayerGame
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerGameRepository
import javax.inject.Inject

@HiltViewModel
class RankingFragmentViewModel @Inject constructor(
    private val playerGameRepository: PlayerGameRepository
) : ViewModel() {


    //Not necesary inicialization:
    //Spinner select default filter when is created.
    private val _gameModeFilter: MutableLiveData<GameMode> = MutableLiveData()
    val gameModeFilter: LiveData<GameMode> = _gameModeFilter

    private val games: LiveData<List<PlayerGame>> = _gameModeFilter.switchMap { gameMode ->
        queryGames(gameMode)
    }

    val onGamesList:LiveData<Event<List<PlayerGame>>> = games.map {
        Event(it)
    }
    val emptyViewVisibility: LiveData<Int> = games.map {
        if (it.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun queryGames(gameMode: GameMode): LiveData<List<PlayerGame>> = when (gameMode) {
        GameMode.ALL -> playerGameRepository.queryAllGames()
        else -> playerGameRepository.queryGamesByFilter(gameMode.mode)
    }


    fun changeGameModeFilter(gameMode: GameMode) {
        _gameModeFilter.value = gameMode
    }

}
