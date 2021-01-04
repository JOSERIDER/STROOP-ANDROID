package es.iessaladillo.pedrojoya.stroop.ui.result

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.StroopDatabase
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerGameRepositoryImp
import es.iessaladillo.pedrojoya.stroop.databinding.ResultFragmentBinding
import kotlinx.android.synthetic.main.result_fragment.*

class ResultFragment:Fragment(R.layout.result_fragment) {


    private val viewModel:ResultFragmentViewModel by viewModels{
        ResultFragmentViewModelFactory(PlayerGameRepositoryImp(StroopDatabase.getInstance(requireContext()).playerGameDao))
    }

    private lateinit var listener: OnToolbarAvailableListener
    private lateinit var binding : ResultFragmentBinding
    private val args:ResultFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        Result
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        setupBinding()
        setGame()
    }

    private fun setGame() {
        viewModel.setGame(args.gameId)
    }

    private fun setupToolbar() = listener.onToolbarCreated(toolbar)

    private fun setupBinding() {
        binding = ResultFragmentBinding.bind(requireView()).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

}
