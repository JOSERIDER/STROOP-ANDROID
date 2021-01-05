package es.iessaladillo.pedrojoya.stroop .ui

import android.content.SharedPreferences
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import es.iessaladillo.pedrojoya.stroop.PREF_KEY_FIRST_TIME
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.databinding.MainActivityBinding


class MainActivity : AppCompatActivity(), OnToolbarAvailableListener{


    private val preferences:SharedPreferences by lazy {
        getSharedPreferences("app_pref", MODE_PRIVATE)
    }


    private val binding :MainActivityBinding by viewBinding()

    private val navController:NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    private val appBarConfiguration:AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val isFistTime = preferences.getBoolean(PREF_KEY_FIRST_TIME, true)
        if(isFistTime) navController.navigate(R.id.assistantFragmentDestination)
    }

    override fun onToolbarCreated(toolbar: Toolbar) {
       setSupportActionBar(toolbar)
       toolbar.setupWithNavController(navController,appBarConfiguration)
    }

}
