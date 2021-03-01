package es.iessaladillo.pedrojoya.stroop.ui.assistant

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import es.iessaladillo.pedrojoya.stroop.PREF_KEY_FIRST_TIME
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.databinding.AssistantFragmentBinding

class AssistantFragment : Fragment(R.layout.assistant_fragment) {


    private val binding:AssistantFragmentBinding by viewBinding()

    private val preferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    }
    private lateinit var listener: OnToolbarAvailableListener

    private val adapter: AssistantFragmentAdapter by lazy {
        AssistantFragmentAdapter(requireActivity().application).apply {
            finishButton = { finish() }
        }
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
        setupToolbar()
        setupViewPager()
    }

    private fun setupToolbar() {
        listener.onToolbarCreated(binding.toolbar)
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
        }.attach()
    }

    private fun finish() {
        preferences.edit().putBoolean(PREF_KEY_FIRST_TIME, false).apply()
        requireActivity().onBackPressed()
    }

}