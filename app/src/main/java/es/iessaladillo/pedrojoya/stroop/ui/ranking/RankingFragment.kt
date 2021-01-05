package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import es.iessaladillo.pedrojoya.stroop.MESSAGE_ID_HELP_RANKING
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.enums.GameMode
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.base.observeEvent
import es.iessaladillo.pedrojoya.stroop.data.StroopDatabase
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerGameRepositoryImp
import es.iessaladillo.pedrojoya.stroop.databinding.RankingFragmentBinding
import es.iessaladillo.pedrojoya.stroop.extensions.doOnItemSelected
import kotlinx.android.synthetic.main.ranking_fragment.*

class RankingFragment : Fragment(R.layout.ranking_fragment) {


    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    private val viewModel: RankingFragmentViewModel by viewModels {
        RankingFragmentViewModelFactory(
            PlayerGameRepositoryImp(StroopDatabase.getInstance(requireContext()).playerGameDao),
            requireActivity().application
        )
    }

    private val binding:RankingFragmentBinding by viewBinding()

    private val navController: NavController by lazy {
        findNavController()
    }

    private val rankingAdapter: RankingFragmentAdapter by lazy {
        RankingFragmentAdapter(viewModel).apply {
            onItemClick = {
                itemSelected(it)
            }
        }
    }

    private lateinit var listener: OnToolbarAvailableListener

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
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.helpDialogDestination -> navController.navigate(
                RankingFragmentDirections.openHelpDialogFragment(
                    MESSAGE_ID_HELP_RANKING
                )
            )
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeLiveData()
        setupViews()
    }

    private fun setupToolbar() = listener.onToolbarCreated(binding.toolbar)

    private fun observeLiveData() {
        observeGames()
        observeEmptyView()
    }

    private fun observeEmptyView() {
        viewModel.emptyViewVisibility.observe(viewLifecycleOwner, Observer {
            binding.lblEmptyViewRanking.visibility = it
        })
    }

    private fun observeGames() {
        viewModel.onGamesList.observeEvent(viewLifecycleOwner) { gameList ->
            rankingAdapter.submitList(gameList)
        }
    }

    private fun setupViews() {
        setupRcl()
        setupSpinner()
    }

    private fun setupRcl() {
        binding.rclRanking.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            itemAnimator = DefaultItemAnimator()
            adapter = rankingAdapter
        }
    }

    private fun setupSpinner() {

        val list: MutableList<GameMode> = enumValues<GameMode>().toMutableList()
        val spnAdapter = ArrayAdapter<GameMode>(
            requireContext(), android.R.layout.simple_spinner_item, list
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spnRankingFilter.run {
            adapter = spnAdapter
            val pos = list.indexOf(
                GameMode.toGameMode(
                    preferences.getString(
                        getString(R.string.prefRankingFilter_key),
                        getString(R.string.prefRankingFilter_defaultValue)
                    )!!
                )
            )
            setSelection(pos)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.spnRankingFilter.onItemSelectedListener =
            binding.spnRankingFilter.doOnItemSelected { changeGameMode(it as GameMode) }
    }

    private fun changeGameMode(gameMode: GameMode) {
        viewModel.changeGameModeFilter(gameMode)
    }


    private fun itemSelected(position: Int) {
        val gameId = rankingAdapter.currentList[position].game.id
        navController.navigate(RankingFragmentDirections.openResultFragment(gameId))
    }


}