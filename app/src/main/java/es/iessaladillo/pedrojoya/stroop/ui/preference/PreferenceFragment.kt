package es.iessaladillo.pedrojoya.stroop.ui.preference

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.EditTextPreference.OnBindEditTextListener
import androidx.preference.PreferenceFragmentCompat
import es.iessaladillo.pedrojoya.stroop.R


class PreferenceFragment:PreferenceFragmentCompat() {

    companion object{
        fun newInstance():PreferenceFragment = PreferenceFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey)

        val num_attempts = findPreference<EditTextPreference>(getString(R.string.prefAttempts_key))

        num_attempts?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }


}