package es.iessaladillo.pedrojoya.stroop.ui.player.playerCreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.NO_AVATAR_SELECTED
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.databinding.AvatarSelectionCardBinding
import es.iessaladillo.pedrojoya.stroop.databinding.PlayerCreationFragmentBinding
import es.iessaladillo.pedrojoya.stroop.extensions.getValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.avatar_selection_card.view.*

typealias OnclikItem = (position: Int) -> Unit

class PlayerCreationAdapter(private val viewModel: PlayerCreationViewModel) :
    RecyclerView.Adapter<PlayerCreationAdapter.ViewHolder>() {

    lateinit var onAvatarSelected: OnclikItem


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.avatar_selection_card, parent, false)
        val binding = AvatarSelectionCardBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = avatars.size


    inner class ViewHolder(private val binding: AvatarSelectionCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onAvatarSelected(bindingAdapterPosition) }
        }

        fun bind(position: Int) {
            val avatarSelected = viewModel.avatarSelectedPosition.getValue(NO_AVATAR_SELECTED)
          binding.imgAvatarCard.setImageResource(avatars[position])
           binding.viewAvatarCardIsSelected.visibility =
                if (avatarSelected == position) View.VISIBLE else View.INVISIBLE

        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}


