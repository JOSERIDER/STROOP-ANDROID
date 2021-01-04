package es.iessaladillo.pedrojoya.stroop.ui.preference

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.iessaladillo.pedrojoya.stroop.MESSAGE_ID_HELP_SETTINGS
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import kotlinx.android.synthetic.main.setting_fragment.*

class SettingFragment:Fragment(R.layout.setting_fragment) {


    private lateinit var listener: OnToolbarAvailableListener

    private val navController:NavController by lazy {
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
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.helpDialogDestination -> navController.navigate(SettingFragmentDirections.openHelpDialogFragment(
                MESSAGE_ID_HELP_SETTINGS))
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fcSettingPreference, PreferenceFragment.newInstance())
        }
        listener.onToolbarCreated(toolbar)
    }



}