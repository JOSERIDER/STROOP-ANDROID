package es.iessaladillo.pedrojoya.stroop.ui.result

import android.content.Context
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.databinding.ResultFragmentBinding

@AndroidEntryPoint
class ResultFragment : Fragment(R.layout.result_fragment) {

    private val viewModel: ResultFragmentViewModel by viewModels()

    private lateinit var listener: OnToolbarAvailableListener

    private  val binding: ResultFragmentBinding by viewBinding()

    private val args: ResultFragmentArgs by navArgs()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupBinding()
        setGame()
    }

    private fun setGame() {
        viewModel.setGame(args.gameId)
    }

    private fun setupToolbar() = listener.onToolbarCreated(binding.toolbar)

    private fun setupBinding() {
        binding.run {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}
