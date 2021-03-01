package es.iessaladillo.pedrojoya.stroop.ui.player.playerEdition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.NO_AVATAR_SELECTED
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.databinding.AvatarSelectionCardBinding
import es.iessaladillo.pedrojoya.stroop.extensions.getValue

typealias OnAvatarClickItem = (position: Int, avatarResId: Int) -> Unit

class AvatarEditionCardAdapter(private val viewModel: PlayerEditionViewModel) :
    RecyclerView.Adapter<AvatarEditionCardAdapter.ViewHolder>() {

    lateinit var onAvatarSelected: OnAvatarClickItem

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvatarEditionCardAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AvatarSelectionCardBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = avatars.size


    inner class ViewHolder(private val binding: AvatarSelectionCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onAvatarSelected(
                    bindingAdapterPosition,
                    avatars[bindingAdapterPosition]
                )
            }
        }

        fun bind(position: Int) {
            val currentPlayerAvatar = viewModel.playerAvatar.getValue(NO_AVATAR_SELECTED)
            val currentCardAvatar = avatars[position]
           binding.imgAvatarCard.setImageResource(currentCardAvatar)
            binding.viewAvatarCardIsSelected.visibility =
                if (currentPlayerAvatar == currentCardAvatar) View.VISIBLE else View.INVISIBLE

        }

    }

    override fun onBindViewHolder(holder: AvatarEditionCardAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }
}


