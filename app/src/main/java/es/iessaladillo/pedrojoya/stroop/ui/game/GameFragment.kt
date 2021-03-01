package es.iessaladillo.pedrojoya.stroop.ui.game

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import es.iessaladillo.pedrojoya.stroop.NO_GAME
import es.iessaladillo.pedrojoya.stroop.NO_PLAYER
import es.iessaladillo.pedrojoya.stroop.PREF_KEY_CURRENT_PLAYER_ID_KEY
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.getLongLiveData
import es.iessaladillo.pedrojoya.stroop.base.observeEvent
import es.iessaladillo.pedrojoya.stroop.data.StroopDatabase
import es.iessaladillo.pedrojoya.stroop.data.repository.GameRepositoryImp
import es.iessaladillo.pedrojoya.stroop.databinding.GameFragmentBinding

@AndroidEntryPoint
class GameFragment : Fragment(R.layout.game_fragment) {

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    private val navController: NavController by lazy {
        findNavController()
    }

    private val viewModel: GameViewModel by viewModels()

    private lateinit var binding: GameFragmentBinding
    private var gameTime = 0
    private var wordTime = 0
    private var attempts = 0

    private val appPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences("app_pref", AppCompatActivity.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkCurrentUser()
        setupBinding()
        getPreferences()
        observeEvents()
    }

    private fun checkCurrentUser() {
        appPreferences.getLongLiveData(PREF_KEY_CURRENT_PLAYER_ID_KEY, NO_PLAYER)
            .observe(viewLifecycleOwner) { userId ->
                if (userId == NO_PLAYER) {
                    navController.navigate(GameFragmentDirections.openPlayerSelectionFromGameFragment())
                } else {
                    viewModel.setCurrentUser(userId)
                    startGame()
                }
            }

    }

    private fun setupBinding() {
        binding = GameFragmentBinding.bind(requireView()).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun getPreferences() {
        preferences.run {
            gameTime = getString(
                getString(R.string.prefGameTime_key),
                requireActivity().resources.getString(R.string.prefGameTime_defaultValue)
            )?.toInt()!!

            wordTime = getString(
                getString(R.string.prefWordTime_key),
                requireActivity().resources.getString(R.string.prefWordTime_defaultValue)
            )?.toInt()!!
            attempts = getString(
                getString(R.string.prefAttempts_key),
                getString(R.string.prefAttempts_defaultValue)
            )?.toInt()!!
        }

    }

    private fun observeEvents() {
        observeGame()
        observeMessage()
    }

    private fun observeMessage() {
        viewModel.onShowMessage.observeEvent(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeGame() {
        viewModel.onGameFinish.observeEvent(viewLifecycleOwner) { gameId ->
            if (gameId == NO_GAME) requireActivity().onBackPressed()
            navController.navigate(GameFragmentDirections.openResultFragmentFromGameFragment(gameId))
        }
    }


    private fun startGame() {
        viewModel.setAttemptsNumberDefault(attempts)
        viewModel.startGameThread(gameTime, wordTime)
    }


}