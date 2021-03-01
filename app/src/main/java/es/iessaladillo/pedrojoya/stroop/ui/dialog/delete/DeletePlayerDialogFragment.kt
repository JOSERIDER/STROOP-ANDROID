package es.iessaladillo.pedrojoya.stroop.ui.dialog.delete

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.Event

@Suppress("UNCHECKED_CAST")
class DeletePlayerDialogFragment : DialogFragment() {

    private val viewModel: ViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.player_deletion_title)
            .setMessage(R.string.player_deletion_message)
            .setPositiveButton(R.string.player_deletion_yes) { _, _ ->
                viewModel.deletePlayer()
            }
            .setNegativeButton(R.string.player_deletion_no) { _, _ ->
                dismiss()
            }
            .create()
    }

    class ViewModel :
        androidx.lifecycle.ViewModel() {

        private val _onDeletePlayer:MutableLiveData<Event<Boolean>> = MutableLiveData()
        val onDeletePlayer:LiveData<Event<Boolean>> = _onDeletePlayer

        fun deletePlayer() {
            _onDeletePlayer.value = Event(true)
        }
    }
}