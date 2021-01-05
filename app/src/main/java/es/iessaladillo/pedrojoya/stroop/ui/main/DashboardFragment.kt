package es.iessaladillo.pedrojoya.stroop.ui.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.iessaladillo.pedrojoya.stroop.MESSAGE_ID_HELP_DASHBOARD
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.base.observeEvent
import es.iessaladillo.pedrojoya.stroop.data.StroopDatabase
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepositoryImp
import es.iessaladillo.pedrojoya.stroop.databinding.DashboardFragmentBinding
import kotlinx.android.synthetic.main.dashboard_fragment.*

class DashboardFragment : Fragment(R.layout.dashboard_fragment) {


    private val viewModel: DashboardFragmentViewModel by viewModels {
        DashboardFragmentViewModelFactory(
            PlayerRepositoryImp(StroopDatabase.getInstance(requireContext()).playerDao),
            requireActivity().application
        )
    }
    private val navController: NavController by lazy {
        findNavController()
    }

    private lateinit var dashboardBinding: DashboardFragmentBinding

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
                DashboardFragmentDirections.openHelpDialogFragment(
                    MESSAGE_ID_HELP_DASHBOARD
                )
            )
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding()
        setupViews()

    }

    override fun onStart() {
        super.onStart()
        listener.onToolbarCreated(dashboardBinding.toolbar)
    }
    private fun setupBinding() {
        dashboardBinding = DashboardFragmentBinding.bind(requireView()).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            playDirection = DashboardFragmentDirections.openGameFragment()
            settingsDirection = DashboardFragmentDirections.openSettingFragment()
            rankingDirection = DashboardFragmentDirections.openRankingFragment()
            assistantDirection = DashboardFragmentDirections.openAssistantFragment()
            playerDirection = DashboardFragmentDirections.openPlayerSelectionFragment()
            aboutDirection = DashboardFragmentDirections.openAboutFragment()
        }
    }


    private fun setupViews() {
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.newDestination.observeEvent(viewLifecycleOwner) { directionId ->
            navigate(
                directionId
            )
        }
    }

    private fun navigate(directionId: Int) {
        navController.navigate(directionId)
    }

}