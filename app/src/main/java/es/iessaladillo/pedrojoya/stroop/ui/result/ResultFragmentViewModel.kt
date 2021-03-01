package es.iessaladillo.pedrojoya.stroop.ui.result

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iessaladillo.pedrojoya.stroop.data.pojo.PlayerGame
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerGameRepository
import javax.inject.Inject

@HiltViewModel
class ResultFragmentViewModel @Inject constructor(private val playerGameRepository: PlayerGameRepository):ViewModel() {

    private val _currentGame:MutableLiveData<Long> = MutableLiveData()

    val playerGame:LiveData<PlayerGame> = _currentGame.switchMap { id ->
        playerGameRepository.queryGame(id)
    }

    fun setGame(gameId:Long){
        _currentGame.value = gameId
    }
}