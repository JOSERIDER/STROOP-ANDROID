package es.iessaladillo.pedrojoya.stroop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.base.PreferencesManager


class MainActivity : AppCompatActivity(), OnToolbarAvailableListener {


    private val preferencesManager: PreferencesManager by lazy {
        PreferencesManager(this)
    }

    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        observePreferencesFlow()
    }

    private fun observePreferencesFlow() {
        preferencesManager.isFirstTimeFlow.asLiveData().observe(this, Observer {
            if (it) navController.navigate(R.id.assistantFragmentDestination)
        })
    }

    override fun onToolbarCreated(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

}
