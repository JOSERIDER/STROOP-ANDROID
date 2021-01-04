package es.iessaladillo.pedrojoya.stroop.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.repository.PlayerRepository

@Suppress("UNCHECKED_CAST")
class DashboardFragmentViewModelFactory(private val playerRepository: PlayerRepository, private val application: Application):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DashboardFragmentViewModel(playerRepository,application) as T
}