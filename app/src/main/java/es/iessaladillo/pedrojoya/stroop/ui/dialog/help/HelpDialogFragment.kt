package es.iessaladillo.pedrojoya.stroop.ui.dialog.help

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import es.iessaladillo.pedrojoya.stroop.*

class HelpDialogFragment : DialogFragment() {


    private val args: HelpDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setPositiveButton(getString(R.string.help_accept)) { _,_ -> dismiss()}
            .setTitle(getString(R.string.help_title))
            .setMessage(obtainMessage())
            .create()
    }


    private fun obtainMessage():String {
        val messageMap: Map<Int, String> = mapOf(
            Pair(MESSAGE_ID_HELP_DASHBOARD, getString(R.string.dashboard_help_description)),
            Pair(MESSAGE_ID_HELP_SETTINGS,getString(R.string.settings_help_description)),
            Pair(MESSAGE_ID_HELP_RANKING, getString(R.string.ranking_help_description)),
            Pair(MESSAGE_ID_HELP_PLAYER_SELECTION,getString(R.string.player_selection_help_description)),
            Pair(MESSAGE_ID_HELP_PLAYER_CREATION, getString(R.string.player_creation_help_description)),
            Pair(MESSAGE_ID_HELP_PLAYER_EDITION,getString(R.string.player_edition_help_description))
        )

        if (messageMap.containsKey(args.fragmentId).not()){
          throw IllegalArgumentException("Fragment id not valid.")
        }

        return messageMap.getValue(args.fragmentId)
    }




}