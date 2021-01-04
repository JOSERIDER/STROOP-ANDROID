package es.iessaladillo.pedrojoya.stroop.ui.player.playerEdition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.NO_AVATAR_SELECTED
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.extensions.getValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.avatar_selection_card.view.*

typealias OnAvatarClickItem = (position: Int, avatarResId: Int) -> Unit

class AvatarEditionCardAdapter(private val viewModel: PlayerEditionViewModel) :
    RecyclerView.Adapter<AvatarEditionCardAdapter.ViewHolder>() {

    lateinit var onAvatarSelected: OnAvatarClickItem


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvatarEditionCardAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.avatar_selection_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = avatars.size


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView.rootView), LayoutContainer {

        init {
            containerView.rootView.setOnClickListener {
                onAvatarSelected(
                    bindingAdapterPosition,
                    avatars[bindingAdapterPosition]
                )
            }
        }

        fun bind(position: Int) {
            val currentPlayerAvatar = viewModel.playerAvatar.getValue(NO_AVATAR_SELECTED)
            val currentCardAvatar = avatars[position]
            containerView.img_avatarCard.setImageResource(currentCardAvatar)
            containerView.view_avatarCard_isSelected.visibility =
                if (currentPlayerAvatar == currentCardAvatar) View.VISIBLE else View.INVISIBLE

        }

    }

    override fun onBindViewHolder(holder: AvatarEditionCardAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }
}


