package es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import es.iessaladillo.pedrojoya.stroop.MESSAGE_ID_HELP_PLAYER_SELECTION
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.StroopDatabase
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepositoryImp
import es.iessaladillo.pedrojoya.stroop.databinding.PlayerSelectionFragmentBinding
import kotlinx.android.synthetic.main.player_selection_fragment.*

@AndroidEntryPoint
class PlayerSelectionFragment : Fragment(R.layout.player_selection_fragment) {

    private lateinit var listener: OnToolbarAvailableListener
    private lateinit var binding: PlayerSelectionFragmentBinding

    private val navController: NavController by lazy {
        findNavController()
    }
    private val playerSelectionAdapter: PlayerSelectionAdapter by lazy {
        PlayerSelectionAdapter(viewModel).apply {
            onPlayerSelected = { playerId -> selectPlayer(playerId) }
        }
    }

    private val viewModel: PlayerSelectionViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
        try {
            listener = context as OnToolbarAvailableListener
        } catch (e: ClassCastException) {
            throw RuntimeException(
                "Activity must implement ToolbarInFragmentListener interface"
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.helpDialogDestination -> navController.navigate(
                PlayerSelectionFragmentDirections.openHelpDialogFragment(
                    MESSAGE_ID_HELP_PLAYER_SELECTION
                )
            )
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding()
        listener.onToolbarCreated(binding.toolbar)
        observeLiveData()
        setupViews()
    }

    private fun selectPlayer(playerId: Long) {
        viewModel.changeCurrentPlayer(playerId)
    }

    private fun setupBinding() {
        binding = PlayerSelectionFragmentBinding.bind(requireView()).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun observeLiveData() {
        observePlayers()
        observePlayerId()
    }

    private fun observePlayerId() {
        viewModel.currentPlayerId.observe(viewLifecycleOwner, Observer {
            playerSelectionAdapter.notifyDataSetChanged()
        })
    }

    private fun observePlayers() {
        viewModel.players.observe(viewLifecycleOwner, Observer { playersList ->
            playerSelectionAdapter.submitList(playersList)
        })
    }

    private fun setupViews() {
        binding.btnPlayerSelectionAddPlayer.setOnClickListener { createPlayer() }
        binding.lblPlayerSelectionAvatarEdit.setOnClickListener { editPlayer() }
        setupRcl()
    }

    private fun editPlayer() {
        navController.navigate(PlayerSelectionFragmentDirections.openPlayerEditionFragment(viewModel.currentPlayer.value?.id!!))
    }

    private fun setupRcl() {
       binding.rclPlayerSelection.run {
            layoutManager = GridLayoutManager(
                requireContext(),
                requireActivity().resources.getInteger(R.integer.player_selection_numColumns)
            )
            itemAnimator = DefaultItemAnimator()
            adapter = playerSelectionAdapter
        }
    }

    private fun createPlayer() {
        navController.navigate(R.id.open_playerCreationFragment)
    }


}