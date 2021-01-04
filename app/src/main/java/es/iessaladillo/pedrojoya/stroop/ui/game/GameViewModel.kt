package es.iessaladillo.pedrojoya.stroop.ui.game

import android.app.Application
import android.content.SharedPreferences
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.NO_GAME
import es.iessaladillo.pedrojoya.stroop.POINTS_PER_CORRECT_ANSWER
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.base.enums.GameMode
import es.iessaladillo.pedrojoya.stroop.base.enums.WordColor
import es.iessaladillo.pedrojoya.stroop.base.getStringLiveData
import es.iessaladillo.pedrojoya.stroop.data.pojo.Game
import es.iessaladillo.pedrojoya.stroop.data.repository.GameRepository
import es.iessaladillo.pedrojoya.stroop.extensions.getValue
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


class GameViewModel(
    private val application: Application,
    private val gameRepository: GameRepository
) : ViewModel() {

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    //Data set ☟☟☟☟
    private val words: List<String> = listOf(
        getString(R.string.game_blue),
        getString(R.string.game_green),
        getString(R.string.game_red),
        getString(R.string.game_yellow)
    )
    private val colors: Map<WordColor, Int> = mapOf(
        Pair(WordColor.BLUE, R.color.gameBlue),
        Pair(WordColor.GREEN, R.color.gameGreen),
        Pair(WordColor.RED, R.color.gameRed),
        Pair(WordColor.YELLOW, R.color.gameYellow)
    )

    @Volatile
    private var isGameFinished: Boolean = false

    @Volatile
    private var currentWordMillis: Int = 0

    @Volatile
    private var millisUntilFinished: Int = 0

    private val handler: Handler = Handler()

    private var incorrectCount: Int = 0

    val gameMode: LiveData<GameMode> = preferences.getStringLiveData(
        getString(R.string.prefGameMode_key),
        getString(R.string.prefGameMode_defaultValue)
    ).map { mode -> GameMode.toGameMode(mode) as GameMode }


    private val _wordColor: MutableLiveData<WordColor> = MutableLiveData() // TODO

    val wordColor: LiveData<Int> = _wordColor.map { wordColor ->
        colors.getValue(wordColor)
    } // Will be used in layout.

    private val _word: MutableLiveData<String> = MutableLiveData()
    val word: LiveData<String> = _word //Will be used in layout

    private val _wordsCount: MutableLiveData<Int> = MutableLiveData(0)
    val wordsCount: LiveData<Int> = _wordsCount

    private val _correctCount: MutableLiveData<Int> = MutableLiveData(0)
    val correctCount: LiveData<Int> = _correctCount


    private val _attemptsCount: MutableLiveData<Int> = MutableLiveData(0)
    val attemptsCount: LiveData<Int> = _attemptsCount

    private val _pointsCount: MutableLiveData<Int> = MutableLiveData(0)
    val pointsCount: LiveData<Int> = _pointsCount

    private val _currentUser: MutableLiveData<Long> = MutableLiveData()

    private val _gameTime: MutableLiveData<Int> = MutableLiveData()
    val gameTime: LiveData<Int> = _gameTime //Will be used in layout

    private val _millisToFinished: MutableLiveData<Int> = MutableLiveData()
    val millisToFinished: LiveData<Int> = _millisToFinished //Will be used in layout

    //Events ☟☟☟☟
    private val _onGameFinish: MutableLiveData<Event<Long>> = MutableLiveData()
    val onGameFinish: LiveData<Event<Long>> = _onGameFinish

    private val _onShowMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val onShowMessage: LiveData<Event<String>> = _onShowMessage


    private fun getString(resId: Int) = application.getString(resId)

    private fun onGameTimeTick(millisUntilFinished: Int) {
        _millisToFinished.postValue(millisUntilFinished)
    }

    private fun onGameTimeFinish() {
        isGameFinished = true
        val points = pointsCount.getValue(0)
        val correctWords = correctCount.getValue(0)
        val player = _currentUser.getValue(0)
        val gameMode = gameMode.value?.mode!!
        val incorrect = incorrectCount
        val words = wordsCount.getValue(0)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(gameTime.getValue(0).toLong()).toInt()

        val newGame = Game(0, points, player, gameMode, words, correctWords, incorrect, minutes)
        insertGame(newGame)
    }

    private fun insertGame(game: Game) {
        thread {
            try {
                val gameId = gameRepository.insertGame(game)
                _onGameFinish.postValue(Event(gameId))
            } catch (e: Exception) {
                _onShowMessage.postValue(Event(application.getString(R.string.player_creation_error_inserting_player)))
                _onGameFinish.postValue(Event(NO_GAME))
                return@thread
            }
        }
    }


    init {
        _wordColor.value = enumValues<WordColor>().random()
    }

    private fun nextWord() {
        if (gameMode.value == GameMode.ATTEMPS) {
            if (_attemptsCount.getValue(0) <= 0) onGameTimeFinish()
        }

        _wordColor.postValue(enumValues<WordColor>().random())
        _word.postValue(words.random())
        _wordsCount.postValue(_wordsCount.value?.inc())
    }


    fun checkRight() {
        currentWordMillis = 0

        val color = _wordColor.value!!
        val word = word.getValue("")

        if (wordMatchColor(color, word)) {
            _correctCount.value = _correctCount.value?.inc()
            _pointsCount.value = calculatePoints(_correctCount.getValue(0))
        } else {
            incorrectCount = incorrectCount.inc()
        }

        if (!wordMatchWithAttempts(color, word)) {
            _attemptsCount.value = _attemptsCount.value?.dec()
        }

        nextWord()
    }

    fun checkWrong() {
        currentWordMillis = 0
        val color = _wordColor.value!!
        val word = word.getValue("")

        if (wordNotMatchColor(color, word)) {
            _correctCount.value = _correctCount.value?.inc()
            _pointsCount.value = calculatePoints(_correctCount.getValue(0))
        } else {
            incorrectCount = incorrectCount.inc()
        }

        if (wordMatchWithAttempts(color, word)) {
            _attemptsCount.value = _attemptsCount.value?.dec()
        }

        nextWord()
    }


    fun startGameThread( gameTime: Int, wordTime: Int) {
        millisUntilFinished = gameTime
        currentWordMillis = 0
        isGameFinished = false
        val checkTimeMillis: Int = wordTime / 2
        _gameTime.value = gameTime
        nextWord()
        onGameTimeTick(millisUntilFinished)
        thread {
            try {
                while (!isGameFinished) {
                    Thread.sleep(checkTimeMillis.toLong())
                    // Check and publish on main thread.
                    handler.post {
                        if (!isGameFinished) {
                            if (currentWordMillis >= wordTime) {
                                currentWordMillis = 0
                                nextWord()
                            }
                            currentWordMillis += checkTimeMillis
                            millisUntilFinished -= checkTimeMillis
                            onGameTimeTick(millisUntilFinished)
                            if (millisUntilFinished <= 0) {
                                onGameTimeFinish()
                            }
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        isGameFinished = true
    }

    private fun wordMatchColor(color: WordColor, word: String) = color.color == word

    private fun wordNotMatchColor(color: WordColor, word: String) = color.color != word

    /**
     * @return Check if word matches color and if the game mode is attempts.
     */
    private fun wordMatchWithAttempts(color: WordColor, word: String) =
        wordMatchColor(color, word) && gameMode.value == GameMode.ATTEMPS

    private fun calculatePoints(correctWords: Int) = correctWords * POINTS_PER_CORRECT_ANSWER

    fun setCurrentUser(userId: Long) {
        _currentUser.value = userId
    }

    fun setAttemptsNumberDefault(numberOfAttempts: Int) {
        _attemptsCount.value = numberOfAttempts
    }

}