package es.iessaladillo.pedrojoya.stroop.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesManager(context: Context) {

    private val dataStorePreferences: DataStore<Preferences> = context.createDataStore(
        name = "app_pref"
    )

    companion object {
        val NO_PLAYER = preferencesKey<Long>("NO_PLAYER")
        val PREF_KEY_CURRENT_PLAYER_ID_KEY = preferencesKey<String>("prefCurrentPlayerId")
        val FIRST_TIME = preferencesKey<Boolean>("prefFirstTime")
    }


    suspend fun setFirstTime(isFirstTime: Boolean) {
        dataStorePreferences.edit {
            if (it[FIRST_TIME] == null) {
                it[FIRST_TIME] = true
            } else {
                it[FIRST_TIME] = isFirstTime
            }

        }
    }


    val isFirstTimeFlow: Flow<Boolean> = dataStorePreferences.data.map {
        it[FIRST_TIME] ?: true
    }


}