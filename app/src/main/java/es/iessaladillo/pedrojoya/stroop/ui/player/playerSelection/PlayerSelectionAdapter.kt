package es.iessaladillo.pedrojoya.stroop.ui.player.playerSelection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.NO_PLAYER
import es.iessaladillo.pedrojoya.stroop.data.pojo.Player
import es.iessaladillo.pedrojoya.stroop.databinding.PlayerSelectionItemBinding
import es.iessaladillo.pedrojoya.stroop.extensions.getValue

typealias OnClickItemLong = (id:Long) -> Unit
class PlayerSelectionAdapter(private val viewModel: PlayerSelectionViewModel) :
   RecyclerView.Adapter<PlayerSelectionAdapter.ViewHolder>() {

    private var currentList: List<Player> = listOf()

    lateinit var onPlayerSelected:OnClickItemLong

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: PlayerSelectionItemBinding = PlayerSelectionItemBinding.inflate(
            layoutInflater, parent, false
        )
        return ViewHolder(binding)
    }


    fun submitList(list: List<Player>){
        currentList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: PlayerSelectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onPlayerSelected(currentList[bindingAdapterPosition].id)
            }
        }

        fun bind(player: Player) {
            setupBinding(player)
            val currentPlayerId = viewModel.currentPlayerId.getValue(NO_PLAYER)
            binding.viewPlayerSelectionIsSelected.visibility =
                if ( currentPlayerId == currentList[bindingAdapterPosition].id) View.VISIBLE else View.INVISIBLE
        }

        private fun setupBinding(player: Player) {
            binding.player = player
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int = currentList.size


}