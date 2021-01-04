package es.iessaladillo.pedrojoya.stroop.ui.assistant

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import es.iessaladillo.pedrojoya.stroop.PREF_KEY_FIRST_TIME
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import kotlinx.android.synthetic.main.about_fragment.toolbar
import kotlinx.android.synthetic.main.assistant_fragment.*

class AssistantFragment : Fragment(R.layout.assistant_fragment) {


    private val prefereces: SharedPreferences by lazy {
        requireContext().getSharedPreferences("app_pref", Context.MODE_PRIVATE)
    }
    private lateinit var listener: OnToolbarAvailableListener

    private val navController: NavController by lazy {
        findNavController()
    }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        setupViewPager()

    }

    private fun setupToolbar() {
        listener.onToolbarCreated(toolbar)
    }

    private fun setupViewPager() {
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()
    }

    private fun finish() {
        prefereces.edit().putBoolean(PREF_KEY_FIRST_TIME, false).apply()
        requireActivity().onBackPressed()
    }

}