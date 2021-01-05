package es.iessaladillo.pedrojoya.stroop.ui.about

import android.content.Context
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.databinding.AboutFragmentBinding
import kotlinx.android.synthetic.main.about_fragment.*

class AboutFragment:Fragment(R.layout.about_fragment) {

    private lateinit var listener: OnToolbarAvailableListener

    private val binding :AboutFragmentBinding  by viewBinding()

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
        listener.onToolbarCreated(binding.toolbar)
    }

}