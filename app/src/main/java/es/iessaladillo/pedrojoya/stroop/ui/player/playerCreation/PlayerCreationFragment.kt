package es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.iessaladillo.pedrojoya.stroop.MESSAGE_ID_HELP_PLAYER_EDITION
import es.iessaladillo.pedrojoya.stroop.NO_AVATAR_SELECTED
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.base.observeEvent
import es.iessaladillo.pedrojoya.stroop.databinding.PlayerCreationFragmentBinding
import es.iessaladillo.pedrojoya.stroop.extensions.getValue
import es.iessaladillo.pedrojoya.stroop.ui.player.playerEdition.PlayerEditionFragmentDirections
import kotlinx.android.synthetic.main.player_creation_fragment.*

@AndroidEntryPoint
class PlayerCreationFragment : Fragment(R.layout.player_creation_fragment) {

    private lateinit var listener: OnToolbarAvailableListener

    private val viewModel: PlayerCreationViewModel by viewModels()

    private lateinit var binding: PlayerCreationFragmentBinding

    private val avatarAdapter: PlayerCreationAdapter by lazy {
        PlayerCreationAdapter(viewModel).apply {
            onAvatarSelected = { selectAvatar(it) }
        }
    }

    private val navController: NavController by lazy {
        findNavController()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.helpDialogDestination -> navController.navigate(
                PlayerEditionFragmentDirections.openHelpDialogFragment(
                    MESSAGE_ID_HELP_PLAYER_EDITION
                )
            )
            else -> return super.onOptionsItemSelected(item)
        }
        return true
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding()
        setupToolbar()
        observeLiveData()
        observeEvents()
        setupViews()
    }

    private fun setupToolbar() {
        listener.onToolbarCreated(binding.toolbar)
    }

    private fun setupBinding() {
        binding = PlayerCreationFragmentBinding.bind(requireView()).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun observeLiveData() {
        observeAvatarSelected()
    }

    private fun observeEvents() {
        observePlayerInserted()
        observeMessage()
    }

    private fun setupViews() {
        setupImeOption()
        setupRcl()
    }

    private fun setupImeOption() {
        binding.playerCreationEdtHeader.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (viewModel.valid.value == true) {
                        viewModel.createPlayer()
                    }
                    true

                }
                else -> false
            }
        }
    }

    private fun setupRcl() {
       binding.rclPlayerCreation.run {
            layoutManager = GridLayoutManager(
                requireContext(),
                requireActivity().resources.getInteger(R.integer.player_creation_numColumns)
            )
            itemAnimator = DefaultItemAnimator()
            adapter = avatarAdapter
        }
    }

    private fun observeAvatarSelected() {
        viewModel.avatarSelectedPosition.observe(
            viewLifecycleOwner,
            { position ->
                if (position != NO_AVATAR_SELECTED) viewModel.changeAvatarSelected(avatars[position])
            })
    }

    private fun observePlayerInserted() {
        viewModel.onPlayerInserted.observeEvent(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
    }

    private fun observeMessage() {
        viewModel.onShowMessage.observeEvent(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectAvatar(avatarSelected: Int) {
        val lastedAvatarSelected = viewModel.avatarSelectedPosition.getValue(NO_AVATAR_SELECTED)
        viewModel.selectAvatar(avatarSelected)
        avatarAdapter.notifyItemChanged(lastedAvatarSelected)
        avatarAdapter.notifyItemChanged(avatarSelected)
    }

}