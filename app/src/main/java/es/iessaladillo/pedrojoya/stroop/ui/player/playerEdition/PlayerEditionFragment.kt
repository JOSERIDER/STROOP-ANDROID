package es.iessaladillo.pedrojoya.stroop.ui.player.playerEdition

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.iessaladillo.pedrojoya.stroop.MESSAGE_ID_HELP_PLAYER_EDITION
import es.iessaladillo.pedrojoya.stroop.NO_AVATAR_SELECTED
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.base.observeEvent
import es.iessaladillo.pedrojoya.stroop.data.StroopDatabase
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepositoryImp
import es.iessaladillo.pedrojoya.stroop.databinding.PlayerEditionFragmentBinding
import es.iessaladillo.pedrojoya.stroop.extensions.getValue
import es.iessaladillo.pedrojoya.stroop.ui.dialog.delete.DeletePlayerDialogFragment
import kotlinx.android.synthetic.main.player_creation_fragment.*
import kotlinx.android.synthetic.main.player_edition_fragment.*
import kotlinx.android.synthetic.main.player_edition_fragment.toolbar

@AndroidEntryPoint
class PlayerEditionFragment : Fragment(R.layout.player_edition_fragment) {

    private val args: PlayerEditionFragmentArgs by navArgs()

    private val viewModel: PlayerEditionViewModel by viewModels()

    private val deleteDialogViewModel: DeletePlayerDialogFragment.ViewModel by activityViewModels()

    private lateinit var listener: OnToolbarAvailableListener
    private lateinit var binding: PlayerEditionFragmentBinding

    private val avatarEditionAdapter: AvatarEditionCardAdapter by lazy {
        AvatarEditionCardAdapter(viewModel).apply {
            onAvatarSelected = { position, avatarResId -> selectAvatar(position, avatarResId) }
        }
    }

    private val navController: NavController by lazy {
        findNavController()
    }

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
        inflater.inflate(R.menu.player_edition, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deletePlayerDialogDestination -> navController.navigate(
                PlayerEditionFragmentDirections.openDeletePlayerDialog(args.playerId)
            )
            R.id.helpDialogDestination -> navController.navigate(
                PlayerEditionFragmentDirections.openHelpDialogFragment(
                    MESSAGE_ID_HELP_PLAYER_EDITION
                )
            )
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding()
        observeLiveData()
        observeEvents()
        setupViews()
        setupRcl()
    }

    private fun setupBinding() {
        binding = PlayerEditionFragmentBinding.bind(requireView()).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun setupViews() {
        setupToolbar()
        setupImeOption()
        viewModel.setCurrentPlayerId(args.playerId)

    }

    private fun setupToolbar() {
        listener.onToolbarCreated(binding.toolbar)
    }

    private fun observeLiveData() {
        observeCurrentPlayer()
        observePlayerInserted()
    }

    private fun observeEvents() {
        observeDeletionPlayer()
        observeMessage()
    }

    private fun observeDeletionPlayer() {
        deleteDialogViewModel.onDeletePlayer.observeEvent(viewLifecycleOwner) {
            viewModel.deletePlayer()
        }
    }

    private fun observeMessage() {
        viewModel.onShowMessage.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observePlayerInserted() {
        viewModel.onPlayerInserted.observeEvent(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
    }


    private fun observeCurrentPlayer() {
        viewModel.currentPlayer.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                requireActivity().onBackPressed()
                return@Observer
            }
            viewModel.setValues()
            //Notifica los cambios para que se marque el avatar actual.
            avatarEditionAdapter.notifyDataSetChanged()
        })
    }

    private fun setupRcl() {
        binding.rclPlayerEdition.run {
            layoutManager = GridLayoutManager(
                requireContext(),
                requireActivity().resources.getInteger(R.integer.player_edition_numColumns)
            )
            itemAnimator = DefaultItemAnimator()
            adapter = avatarEditionAdapter
        }
    }

    private fun selectAvatar(position: Int, avatarResId: Int) {
        val lastedAvatarSelected = viewModel.avatarSelectedPosition.getValue(NO_AVATAR_SELECTED)
        viewModel.selectAvatar(position, avatarResId)
        avatarEditionAdapter.notifyItemChanged(lastedAvatarSelected)
        avatarEditionAdapter.notifyItemChanged(position)
    }


    private fun setupImeOption() {
        binding.playerEditionEdtHeader.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (viewModel.valid.value == true) {
                        viewModel.savePlayer()
                    }
                    true

                }
                else -> false
            }
        }
    }

}